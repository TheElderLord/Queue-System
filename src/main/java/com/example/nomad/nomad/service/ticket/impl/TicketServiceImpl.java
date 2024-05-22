package com.example.nomad.nomad.service.ticket.impl;

import com.example.nomad.nomad.Enum.TicketStatus;
import com.example.nomad.nomad.dto.TicketDto;
import com.example.nomad.nomad.dto.WindowDto;
import com.example.nomad.nomad.exception.ResourceNotFoundException;
import com.example.nomad.nomad.mapper.TicketMapper;
import com.example.nomad.nomad.mapper.WindowMapper;
import com.example.nomad.nomad.model.*;
import com.example.nomad.nomad.repository.TicketRepository;
import com.example.nomad.nomad.service.branch.BranchService;
import com.example.nomad.nomad.service.serviceModel.impl.ServiceServiceImpl;
import com.example.nomad.nomad.service.session.SessionService;
import com.example.nomad.nomad.service.ticket.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Primary
@AllArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;

    private ServiceServiceImpl service;
    private final BranchService branchService;
    private final SessionService sessionService;
    private static final Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);

    @Override
    public List<TicketDto> getTickets() {
        return ticketRepository.findAll().stream().map(TicketMapper::toDto).collect(Collectors.toList());
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
    public TicketDto createTicket(TicketDto newTicket) {
        logger.info("Creating tickets:"+newTicket);
        Ticket ticket = TicketMapper.toEntity(newTicket);
        Branch branch = branchService.getEntityById(newTicket.getBranchId());
        Session session = sessionService.getEntityById(newTicket.getSessionId());

        ServiceModel serviceModel = service.getEntityById(newTicket.getServiceId());

        ticket.setTicketNumber(20);
        ticket.setStatus(TicketStatus.NEW);
        ticket.setRegistrationTime(LocalDateTime.now());
        ticket.setBranch(branch);
        ticket.setSession(session);
        ticket.setServiceModel(serviceModel);
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
