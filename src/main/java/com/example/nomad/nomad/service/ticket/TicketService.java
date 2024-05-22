package com.example.nomad.nomad.service.ticket;

import com.example.nomad.nomad.Enum.TicketStatus;
import com.example.nomad.nomad.dto.TicketDto;
import com.example.nomad.nomad.dto.WindowDto;
import com.example.nomad.nomad.model.Ticket;

import java.util.List;

public interface TicketService {
    List<TicketDto> getTickets();
    List<TicketDto> getTicketsBySessionId(Long id);
    List<TicketDto> getTicketsByServiceModelId(Long id);
    List<TicketDto> getTicketsByBranchId(Long id);
    List<TicketDto> getTicketsByStatus(TicketStatus status);
    WindowDto getTicketWindow(Long id);
    TicketDto getTicketById(Long id);
    TicketStatus getTicketStatus(Long id);
    Long getTicketBookingCode(Long id);


    TicketDto createTicket(TicketDto newTicket);
    TicketDto setStatus(Long id,TicketStatus status);
    TicketDto updateTicket(Long id, TicketDto newTicketBody);
    void deleteTicket(Long id);

    Ticket getEntityById(Long id);




}
