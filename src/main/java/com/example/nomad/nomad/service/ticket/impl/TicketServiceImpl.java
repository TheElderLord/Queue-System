package com.example.nomad.nomad.service.ticket.impl;

import com.example.nomad.nomad.Enum.SessionStatus;
import com.example.nomad.nomad.Enum.TerminalType;
import com.example.nomad.nomad.Enum.TicketStatus;
import com.example.nomad.nomad.dto.*;
import com.example.nomad.nomad.dto.session.SessionByBranchAndStatusDto;
import com.example.nomad.nomad.dto.session.SessionDto;
import com.example.nomad.nomad.dto.ticket.*;
import com.example.nomad.nomad.exception.ForbiddenActionException;
import com.example.nomad.nomad.exception.ResourceNotFoundException;
import com.example.nomad.nomad.mapper.ServiceModelMapper;
import com.example.nomad.nomad.mapper.SessionMapper;
import com.example.nomad.nomad.mapper.TicketMapper;
import com.example.nomad.nomad.mapper.WindowMapper;
import com.example.nomad.nomad.model.*;
import com.example.nomad.nomad.repository.RoleRepository;
import com.example.nomad.nomad.repository.RoleServiceRepository;
import com.example.nomad.nomad.repository.SessionRepository;
import com.example.nomad.nomad.repository.TicketRepository;
import com.example.nomad.nomad.service.branch.BranchService;
import com.example.nomad.nomad.service.operator.OperatorService;
import com.example.nomad.nomad.service.roleService.RoleServiceService;
import com.example.nomad.nomad.service.serviceModel.ServService;
import com.example.nomad.nomad.service.session.SessionService;
import com.example.nomad.nomad.service.ticket.TicketService;
import com.example.nomad.nomad.service.window.WindowService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Service
@Primary
@AllArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final SessionRepository sessionRepository;
    private final RoleServiceRepository roleServiceRepository;
    private final OperatorService operatorService;
    private final RoleServiceService roleServiceService;
    private final ServService servService;
    private final BranchService branchService;
    private final SessionService sessionService;
    private final WindowService windowService;
    private static final Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);

    @Override
    public List<TicketDto> getTickets() {
        return ticketRepository.findAll().stream().map(TicketMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ServiceModelDto> getAvailableServices(Long branchId, Long serviceId) {
        // Get a list of active operators
        List<Session> activeSessionsByBranch = sessionService.getActiveSessionsByBranchId(branchId, true);

//        List<OperatorDto> activeOperators = operatorService.getOnlineOperators();

        // If no active operators, return an empty list of services
        if (activeSessionsByBranch.isEmpty()) {
            return Collections.emptyList();
        }
        List<Operator> activeOperators = activeSessionsByBranch.stream().map(Session::getOperator).toList();

        // Extract the IDs of roles of active operators
        List<Long> roleIds = activeOperators.stream()
                .map(e -> e.getRole().getId())
                .distinct()  // Ensure distinct role IDs
                .toList();

        // Fetch available services based on the role IDs
        List<Long> availableServiceIds = roleIds.stream()
                .flatMap(roleId -> roleServiceService.getRoleServicesByRoleId(roleId).stream())
                .distinct()
                .map(RoleServiceDto::getServiceId)// Ensure distinct services
                .toList();

        // Extract service models from available services
        List<ServiceModel> serviceModels = availableServiceIds.stream()
                .map(servService::getEntityById)
                .distinct()  // Ensure distinct service models
                .toList();
        if (serviceId != null) {
            return serviceModels.stream().map(ServiceModelMapper::toDto).distinct()  // Ensure distinct service models
                    .toList();
        }
        List<ServiceModel> parentServices = serviceModels.stream().map(ServiceModel::getParentService).distinct()  // Ensure distinct service models
                .toList();

        return parentServices.stream().map(ServiceModelMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ServiceModelDto> getChildTickets(Long serviceId) {
        List<ServiceModelDto> childTickets = servService.getChildService(serviceId);
        return childTickets;
    }

    @Override
    public List<TicketDto> getAgentTickets(String agent) {
        return ticketRepository.findAllByAgent(agent).stream()
                .map(TicketMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TicketDto> getTicketsByBranchIdAndStatusAndOperator(SessionByBranchAndStatusDto session) {
        Operator operator = operatorService.getEntityById(session.getOperatorId());
        List<RoleServiceModel> roleServiceModels = roleServiceRepository.findAllByRoleId(operator.getRole().getId());
        List<Ticket> tickets = new ArrayList<>();
        for (RoleServiceModel rsm : roleServiceModels) {
                tickets.addAll(ticketRepository.findAllByServiceModelIdAndStatus(rsm.getServiceModel().getId(),session.getStatus()));
        }
        tickets.sort(Comparator.comparing(Ticket::getRegistrationTime));
        Set<Ticket> set = new HashSet<>(tickets);
        return set.stream().map(TicketMapper::toDto)
                .collect(Collectors.toList());


//        return ticketRepository.findAllByBranchIdAndStatus( session.getBranchId(), session.getStatus()).stream()
//                .map(TicketMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<TicketDto> getOperatorTickets(Long id) {
        return ticketRepository.findAllByOperatorIdAndStatus(id,TicketStatus.INSERVICE).stream()
                .map(TicketMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<TicketDto> getQueueTickets(Long branchId) {
        List<Ticket> tickets = ticketRepository.findAllByBranchIdAndStatus(branchId, TicketStatus.INSERVICE);
        return tickets.stream()
                .map(TicketMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<TicketDto> getTicketsBySessionId(Long id) {
        return ticketRepository.findAllBySessionId(id).stream().map(TicketMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<TicketDto> getTicketsByServiceModelId(Long id) {
        return ticketRepository.findAllByServiceModelId(id).stream().map(TicketMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<TicketDto> getTicketsByBranchId(Long id) {
        return ticketRepository.findAllByBranchId(id).stream().map(TicketMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<TicketDto> getTicketsByStatus(TicketStatus status) {
        return ticketRepository.findAllByStatus(status).stream()
                .map(TicketMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public WindowDto getTicketWindow(Long id) {
        Session session = getEntityById(id).getSession();
        return WindowMapper.toDto(session.getWindow());
    }

    @Override
    public TicketDto getTicketById(Long id) {
        Ticket ticket = getEntityById(id);
        return TicketMapper.toDto(ticket);
    }

    @Override
    public TicketStatus getTicketStatus(Long id) {
        return getEntityById(id).getStatus();
    }

    @Override
    public int getTicketBookingCode(Long id) {
        return getEntityById(id).getBookingCode();
    }


    @Override
    public Session getSessionWithLeastTicketsAndService(Long branchId, Long serviceId) {
        List<Session> sessionByBranch = sessionService.getActiveSessionsByBranchId(branchId, true);
        logger.info("ACTIVE SESSIONS BY BRANCH:" + sessionByBranch.stream().toList());
        int min = Integer.MAX_VALUE;
        int current;
        int index = -1; // Initialize with -1 to handle cases where there are no sessions.

//        SessionByBranchAndStatusDto session = new SessionByBranchAndStatusDto(branchId,serviceId,TicketStatus.NEW);
        for (int i = 0; i < sessionByBranch.size(); i++) {
            current = ticketRepository.findAllBySessionId(sessionByBranch.get(i).getId()).size();
            logger.info("TICKETS BY SESSION:" + current);

            if (current < min) {
                min = current;
                index = i;
            }
        }
        logger.info("LEAST TICKET SESSION:" + sessionByBranch.get(index));

        if (index != -1) {
            // Return the session with the least tickets
            return sessionByBranch.get(index);
        } else {
            // Handle the case where no sessions were found.
            // This could be returning null, throwing an exception, or another appropriate action.
            return null;
        }
    }


    @Override
    public TicketDto createTicket(TicketRegisterDto newTicket) {
        List<Ticket> agentNotFinishedTickets;
        if (newTicket.getTerminalType() == TerminalType.MOBILE) {
            agentNotFinishedTickets = ticketRepository.findAllByAgentAndStatus(newTicket.getAgent(), TicketStatus.NEW);
            if (!agentNotFinishedTickets.isEmpty()) {
                throw new ForbiddenActionException("User has incomplete tickets");
            }
        }

        TicketDto ticketDto = new TicketDto();
        ticketDto.setServiceId(newTicket.getServiceId());
        ticketDto.setBranchId(newTicket.getBranchId());
        ticketDto.setAgent(newTicket.getAgent());

        Ticket ticket = TicketMapper.toEntity(ticketDto);
        Branch branch = branchService.getEntityById(newTicket.getBranchId());
        ServiceModel serviceModel = servService.getEntityById(newTicket.getServiceId());
        ZoneId gmtPlus5 = ZoneId.of("GMT+5");
        ZonedDateTime gmtPlus5ZonedDateTime = ZonedDateTime.now(gmtPlus5);

        ticket.setRegistrationTime(gmtPlus5ZonedDateTime);
        ticket.setStatus(TicketStatus.NEW);
        ticket.setBranch(branch);
        ticket.setServiceModel(serviceModel);
        ticket.setLanguage(newTicket.getLanguage());

        int randomTicketNumber = getAvailableTicketNumber(newTicket.getBranchId());
        ticket.setTicketNumber(randomTicketNumber);

        ticketRepository.save(ticket);
        return TicketMapper.toDto(ticket);
    }

    private int getAvailableTicketNumber(Long branchId) {
        ZoneId zoneId = ZoneId.of("GMT+5"); // or your relevant time zone
        ZonedDateTime startOfDay = ZonedDateTime.now(zoneId).toLocalDate().atStartOfDay(zoneId);
        ZonedDateTime endOfDay = startOfDay.plusDays(1).minusNanos(1);

        // Fetch all tickets for the specific branch and today's date
        Set<Integer> usedTicketNumbers = ticketRepository
                .findAllByBranchIdAndRegistrationTimeBetween(branchId, startOfDay, endOfDay)
                .stream()
                .map(Ticket::getTicketNumber)
                .collect(Collectors.toSet());

        // Find the first available ticket number
        int ticketNumber = 1;
        while (usedTicketNumbers.contains(ticketNumber)) {
            ticketNumber++;
        }

        return ticketNumber;
    }


    @Override
    public TicketDto bookTicket(TicketBookDto bookDto) {
        Ticket ticket = new Ticket();
        ticket.setServiceModel(servService.getEntityById(bookDto.getServiceId()));
        ticket.setBranch(branchService.getEntityById(bookDto.getBranchId()));
        ticket.setAgent(bookDto.getAgent());
        ticket.setBookingTime(bookDto.getBookingTime());
        ticket.setStatus(TicketStatus.BOOKED);
        int bookingCode;
        do {
            bookingCode = new Random().nextInt(100);
        } while (ticketRepository.existsByBookingCode(bookingCode));

        ticket.setBookingCode(bookingCode);
        ticketRepository.save(ticket);
        return TicketMapper.toDto(ticket);
    }

    @Override
    public TicketDto activateBookedTicket(int code) {
        Ticket ticket = ticketRepository.findByBookingCode(code);
        if (ticket == null) {
            return null;
        }
        ticket.setStatus(TicketStatus.NEW);
        ZoneId gmtPlus5 = ZoneId.of("GMT+5");
        ZonedDateTime gmtPlus5ZonedDateTime = ZonedDateTime.now(gmtPlus5);
        ticket.setRegistrationTime(gmtPlus5ZonedDateTime);

        ticketRepository.save(ticket);
        return TicketMapper.toDto(ticket);
    }

    @Override
    @ExceptionHandler(NullPointerException.class)
    public TicketDto callNext(SessionByBranchAndStatusDto session) {
        Operator operator = operatorService.getEntityById(session.getOperatorId());
        List<RoleServiceModel> roleServiceModels = roleServiceRepository.findAllByRoleId(operator.getRole().getId());
        List<Ticket> tickets = new ArrayList<>();
        for (RoleServiceModel rsm : roleServiceModels) {
            tickets.addAll(ticketRepository.findAllByServiceModelIdAndStatus(rsm.getServiceModel().getId(), TicketStatus.NEW));
        }
        tickets.sort(Comparator.comparing(Ticket::getRegistrationTime));

        if (tickets.isEmpty()) {
            return new TicketDto();
        }

        Ticket ticket = tickets.get(0);
        ticket.setStatus(TicketStatus.INSERVICE);
        List<Session> session1 = sessionRepository.findByOperatorIdAndActive(session.getOperatorId(), true);
        if (session1 == null || session1.isEmpty()) {
            throw new ResourceNotFoundException("Active session not found for operator with ID: " + session.getOperatorId());
        }

        ticket.setSession(session1.get(0));
        ticket.setOperator(operatorService.getEntityById(session.getOperatorId()));
        ticket.setWindow(windowService.getEntityById(session.getWindowId()));

        ZoneId gmtPlus5 = ZoneId.of("GMT+5");
        ZonedDateTime gmtPlus5ZonedDateTime = ZonedDateTime.now(gmtPlus5);
        ticket.setServiceStartTime(gmtPlus5ZonedDateTime);

        ticketRepository.save(ticket);
        return TicketMapper.toDto(ticket);
    }


    @Override
    public TicketDto complete(Long id, TicketStatus status) {
        Ticket ticket = getEntityById(id);
        ticket.setStatus(status);
        ZoneId gmtPlus5 = ZoneId.of("GMT+5");
        ZonedDateTime gmtPlus5ZonedDateTime = ZonedDateTime.now(gmtPlus5);
        ticket.setServiceEndTime(gmtPlus5ZonedDateTime);
        ticketRepository.save(ticket);
        return TicketMapper.toDto(ticket);
    }

    @Override
    public TicketDto setStatus(Long id, TicketStatus status) {
        Ticket ticket = getEntityById(id);
        ticket.setStatus(status);
        return TicketMapper.toDto(ticket);
    }

    @Override
    public TicketDto updateTicket(Long id, TicketDto newTicketBody) {
        return null;
    }

    @Override
    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }

    @Override
    public void setRating(TicketRatingDto ticketRatingDto) {
        Ticket ticket = getEntityById(ticketRatingDto.getId());
        ticket.setRating(ticketRatingDto.getRating());
        ticket.setComment(ticketRatingDto.getComment());
        ticketRepository.save(ticket);
    }

    @Override
    public TicketDto redirect(Long id, TicketRedirectDto ticketDto) {
        Ticket ticket = getEntityById(id);
        Operator operator = operatorService.getEntityById(ticketDto.getOperatorId());
        ticket.setOperator(operator);
        ticket.setStatus(TicketStatus.NEW);
        List<SessionDto> session = sessionService.getSessionsByOperatorIdAndStatus(operator.getId());
        ticket.setSession(SessionMapper.toEntity(session.get(0)));
        Window window = windowService.getEntityById(session.get(0).getWindowId());
        ticket.setWindow(window);
        ZoneId gmtPlus5 = ZoneId.of("GMT+5");
        ZonedDateTime gmtPlus5ZonedDateTime = ZonedDateTime.now(gmtPlus5);
        ticket.setRegistrationTime(gmtPlus5ZonedDateTime);
        ticket.setDirected(true);
        ticketRepository.save(ticket);
        return TicketMapper.toDto(ticket);
    }

    @Override
    public Ticket getEntityById(Long id) {
        return ticketRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Ticket does not exist")
        );
    }
}
