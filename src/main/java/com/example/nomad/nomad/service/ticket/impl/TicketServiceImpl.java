package com.example.nomad.nomad.service.ticket.impl;

import com.example.nomad.nomad.Enum.TicketStatus;
import com.example.nomad.nomad.dto.*;
import com.example.nomad.nomad.dto.session.SessionByBranchAndStatusDto;
import com.example.nomad.nomad.dto.session.SessionDto;
import com.example.nomad.nomad.dto.ticket.TicketDto;
import com.example.nomad.nomad.dto.ticket.TicketQueueDto;
import com.example.nomad.nomad.dto.ticket.TicketRegisterDto;
import com.example.nomad.nomad.exception.ResourceNotFoundException;
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
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    private static final Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);

    @Override
    public List<TicketDto> getTickets() {
        return ticketRepository.findAll().stream().map(TicketMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ServiceModelDto> getAvailableServices(Long branchId) {
        // Get a list of active operators
        List<Session> activeSessionsByBranch = sessionService.getActiveSessionsByBranchId(branchId,true);

//        List<OperatorDto> activeOperators = operatorService.getOnlineOperators();

        // If no active operators, return an empty list of services
        if (activeSessionsByBranch.isEmpty()) {
            return Collections.emptyList();
        }
        List<Operator> activeOperators = activeSessionsByBranch.stream().map(e->e.getOperator()).collect(Collectors.toList());

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
        List<ServiceModelDto> serviceModels = availableServiceIds.stream()
                .map(servService::getServiceById)
                .distinct()  // Ensure distinct service models
                .collect(Collectors.toList());

        return serviceModels;
    }

    @Override
    public List<TicketDto> getAgentTickets(String agent) {
        return ticketRepository.findAllByAgent(agent).stream()
                .map(TicketMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TicketDto> getTicketsBySessionBranchIdAndStatus(SessionByBranchAndStatusDto session) {
        return ticketRepository.findAllBySessionIdAndBranchIdAndStatus(session.getSessionId(), session.getBranchId(), session.getStatus()).stream()
                .map(TicketMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<TicketDto> getQueueTickets(Long sessionId,Long branchId) {
        List<Ticket> tickets =  ticketRepository.findAllBySessionIdAndBranchIdAndStatus(sessionId, branchId, TicketStatus.INSERVICE);
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
    public Long getTicketBookingCode(Long id) {
        return getEntityById(id).getBookingCode();
    }


    @Override
    public Session getSessionWithLeastTicketsAndService(Long branchId, Long serviceId) {
        List<SessionDto> sessionByBranch = sessionService.getSessionsByBranchId(branchId);
        int min = Integer.MAX_VALUE;
        int current;
        int index = -1; // Initialize with -1 to handle cases where there are no sessions.

        SessionByBranchAndStatusDto session = new SessionByBranchAndStatusDto(branchId,serviceId,TicketStatus.NEW);
        for (int i = 0; i < sessionByBranch.size(); i++) {
            current = getTicketsBySessionBranchIdAndStatus(session).size();

            if (current < min) {
                min = current;
                index = i;
            }
        }

        if (index != -1) {
            // Return the session with the least tickets
            return SessionMapper.toEntity(sessionByBranch.get(index));
        } else {
            // Handle the case where no sessions were found.
            // This could be returning null, throwing an exception, or another appropriate action.
            return null;
        }
    }

    @Override
    public TicketDto createTicket(TicketRegisterDto newTicket) {
        logger.info("Creating tickets:"+newTicket);

        TicketDto ticketDto = new TicketDto();
        ticketDto.setServiceId(newTicket.getServiceId());
        ticketDto.setBranchId(newTicket.getBranchId());
        ticketDto.setAgent(newTicket.getAgent());

        Ticket ticket = TicketMapper.toEntity(ticketDto);
        Branch branch = branchService.getEntityById(newTicket.getBranchId());
//        Session session = sessionService.getEntityById(newTicket.getSessionId());
        ServiceModel serviceModel = servService.getEntityById(newTicket.getServiceId());
        Session session = getSessionWithLeastTicketsAndService(branch.getId(), serviceModel.getId());
        logger.info(session+" Session");
        ticket.setTicketNumber(new Random().nextInt(9999));
        ticket.setStatus(TicketStatus.NEW);
        ticket.setRegistrationTime(LocalDateTime.now());
        ticket.setBranch(branch);
        ticket.setSession(session);
        ticket.setServiceModel(serviceModel);
        ticketRepository.save(ticket);
        return TicketMapper.toDto(ticket);
    }

    @Override
    public TicketDto callNext(SessionByBranchAndStatusDto session) {
        List<Ticket> tickets = ticketRepository.findAllBySessionIdAndBranchIdAndStatus(session.getSessionId(),session.getBranchId(),TicketStatus.NEW);
        Ticket ticket = tickets.get(0);
        ticket.setStatus(TicketStatus.INSERVICE);
        ticket.setServiceStartTime(LocalDateTime.now());
        ticketRepository.save(ticket);
        return TicketMapper.toDto(ticket);
    }

    @Override
    public TicketDto complete(Long id) {
        Ticket ticket = getEntityById(id);
        ticket.setStatus(TicketStatus.COMPLETED);
        ticket.setServiceEndTime(LocalDateTime.now());
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
    public Ticket getEntityById(Long id) {
        return ticketRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Ticket does not exist")
        );
    }
}
