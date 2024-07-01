package com.example.nomad.nomad.service.ticket.impl;

import com.example.nomad.nomad.Enum.SessionStatus;
import com.example.nomad.nomad.Enum.TerminalType;
import com.example.nomad.nomad.Enum.TicketStatus;
import com.example.nomad.nomad.dto.*;
import com.example.nomad.nomad.dto.session.SessionByBranchAndStatusDto;
import com.example.nomad.nomad.dto.session.SessionDto;
import com.example.nomad.nomad.dto.ticket.TicketBookDto;
import com.example.nomad.nomad.dto.ticket.TicketDto;
import com.example.nomad.nomad.dto.ticket.TicketRedirectDto;
import com.example.nomad.nomad.dto.ticket.TicketRegisterDto;
import com.example.nomad.nomad.exception.ForbiddenActionException;
import com.example.nomad.nomad.exception.ResourceNotFoundException;
import com.example.nomad.nomad.mapper.ServiceModelMapper;
import com.example.nomad.nomad.mapper.SessionMapper;
import com.example.nomad.nomad.mapper.TicketMapper;
import com.example.nomad.nomad.mapper.WindowMapper;
import com.example.nomad.nomad.model.*;
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
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Primary
@AllArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
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
    public List<ServiceModelDto> getAvailableServices(Long branchId,Long serviceId) {
        // Get a list of active operators
        List<Session> activeSessionsByBranch = sessionService.getActiveSessionsByBranchId(branchId,true);

//        List<OperatorDto> activeOperators = operatorService.getOnlineOperators();

        // If no active operators, return an empty list of services
        if (activeSessionsByBranch.isEmpty()) {
            return Collections.emptyList();
        }
        List<Operator> activeOperators = activeSessionsByBranch.stream().map(Session::getOperator).toList();

        // Extract the IDs of roles of active operators
        List<Long> roleIds = activeOperators.stream()
                .map(e->e.getRole().getId())
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
        if(serviceId!=null){
            return  serviceModels.stream().map(ServiceModelMapper::toDto).distinct()  // Ensure distinct service models
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
    public List<TicketDto> getTicketsByOpratorBranchIdAndStatus(SessionByBranchAndStatusDto session) {
        return ticketRepository.findAllByOperatorIdAndBranchIdAndStatus(session.getOperatorId(), session.getBranchId(), session.getStatus()).stream()
                .map(TicketMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<TicketDto> getQueueTickets(Long branchId) {
        List<Ticket> tickets =  ticketRepository.findAllByBranchIdAndStatus(branchId, TicketStatus.INSERVICE);
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
        List<Session> sessionByBranch = sessionService.getActiveSessionsByBranchId(branchId,true);
        logger.info("ACTIVE SESSIONS BY BRANCH:"+sessionByBranch.stream().toList());
        int min = Integer.MAX_VALUE;
        int current;
        int index = -1; // Initialize with -1 to handle cases where there are no sessions.

//        SessionByBranchAndStatusDto session = new SessionByBranchAndStatusDto(branchId,serviceId,TicketStatus.NEW);
        for (int i = 0; i < sessionByBranch.size(); i++) {
            current = ticketRepository.findAllBySessionId(sessionByBranch.get(i).getId()).size();
            logger.info("TICKETS BY SESSION:"+current);

            if (current < min) {
                min = current;
                index = i;
            }
        }
        logger.info("LEAST TICKET SESSION:"+sessionByBranch.get(index));

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
//        logger.info("Creating tickets:"+newTicket);

        List<Ticket> agentNotFinishedTickets;
        if(newTicket.getTerminalType()== TerminalType.MOBILE){
            agentNotFinishedTickets  = ticketRepository.findAllByAgentAndStatus(newTicket.getAgent(), TicketStatus.NEW);
            if(!agentNotFinishedTickets.isEmpty()){
                throw new ForbiddenActionException("User has incomplete tickets");
            }
        }

        TicketDto ticketDto = new TicketDto();
        ticketDto.setServiceId(newTicket.getServiceId());
        ticketDto.setBranchId(newTicket.getBranchId());
        ticketDto.setAgent(newTicket.getAgent());

        Ticket ticket = TicketMapper.toEntity(ticketDto);
        Branch branch = branchService.getEntityById(newTicket.getBranchId());
//       // Session session = sessionService.getEntityById(newTicket.getSessionId());
        ServiceModel serviceModel = servService.getEntityById(newTicket.getServiceId());
        Session session = getSessionWithLeastTicketsAndService(branch.getId(), serviceModel.getId());
        Operator operator = session.getOperator();
        logger.info("Found session"+ session);
        ticket.setOperator(operator);
        ticket.setWindow(session.getWindow());
        LocalDateTime localDateTime = LocalDateTime.now();
        ZoneId almatyZone = ZoneId.of("Asia/Almaty");
        ZonedDateTime almatyZonedDateTime = localDateTime.atZone(almatyZone);
        ticket.setRegistrationTime(almatyZonedDateTime);
        ticket.setStatus(TicketStatus.NEW);
        ticket.setBranch(branch);
        ticket.setSession(session);
        ticket.setServiceModel(serviceModel);
        int randomTicketNumber;
        do {
            randomTicketNumber = new Random().nextInt(100);
        } while (ticketRepository.existsByTicketNumber(randomTicketNumber));

        ticket.setTicketNumber(randomTicketNumber);

        ticketRepository.save(ticket);
        return TicketMapper.toDto(ticket);
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
        if(ticket!=null){
            return null;
        }
        ticket.setStatus(TicketStatus.NEW);
        LocalDateTime localDateTime = LocalDateTime.now();
        ZoneId almatyZone = ZoneId.of("Asia/Almaty");
        ZonedDateTime almatyZonedDateTime = localDateTime.atZone(almatyZone);
        ticket.setRegistrationTime(almatyZonedDateTime);

        ticketRepository.save(ticket);
        return TicketMapper.toDto(ticket);
    }

    @Override
    public TicketDto callNext(SessionByBranchAndStatusDto session) {
        List<Ticket> tickets = ticketRepository.findAllByOperatorIdAndBranchIdAndStatus(session.getOperatorId(),session.getBranchId(),TicketStatus.NEW);
        Ticket ticket = tickets.get(0);
        ticket.setStatus(TicketStatus.INSERVICE);
        LocalDateTime localDateTime = LocalDateTime.now();
        ZoneId almatyZone = ZoneId.of("Asia/Almaty");
        ZonedDateTime almatyZonedDateTime = localDateTime.atZone(almatyZone);
        ticket.setServiceStartTime(almatyZonedDateTime);
        ticketRepository.save(ticket);
        return TicketMapper.toDto(ticket);
    }

    @Override
    public TicketDto complete(Long id,TicketStatus status) {
        Ticket ticket = getEntityById(id);
        ticket.setStatus(status);
        LocalDateTime localDateTime = LocalDateTime.now();
        ZoneId almatyZone = ZoneId.of("Asia/Almaty");
        ZonedDateTime almatyZonedDateTime = localDateTime.atZone(almatyZone);

        ticket.setServiceEndTime(almatyZonedDateTime);
        ticketRepository.save(ticket);
        return TicketMapper.toDto(ticket);
    }

    @Override
    public TicketDto setStatus(Long id,TicketStatus status) {
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
    public void setRating(Long id,int rating) {
        Ticket ticket = getEntityById(id);
        ticket.setRating(rating);
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
        LocalDateTime localDateTime = LocalDateTime.now();
        ZoneId almatyZone = ZoneId.of("Asia/Almaty");
        ZonedDateTime almatyZonedDateTime = localDateTime.atZone(almatyZone);
        ticket.setRegistrationTime(almatyZonedDateTime);
        ticket.setDirected(true);
        ticketRepository.save(ticket);
        return TicketMapper.toDto(ticket);
    }

    @Override
    public Ticket getEntityById(Long id) {
        return ticketRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Ticket does not exist")
        );
    }
}
