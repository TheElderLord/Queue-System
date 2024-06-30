package com.example.nomad.nomad.service.ticket;

import com.example.nomad.nomad.Enum.TicketStatus;
import com.example.nomad.nomad.dto.ServiceModelDto;
import com.example.nomad.nomad.dto.session.SessionByBranchAndStatusDto;
import com.example.nomad.nomad.dto.ticket.TicketBookDto;
import com.example.nomad.nomad.dto.ticket.TicketDto;
import com.example.nomad.nomad.dto.WindowDto;
import com.example.nomad.nomad.dto.ticket.TicketRedirectDto;
import com.example.nomad.nomad.dto.ticket.TicketRegisterDto;
import com.example.nomad.nomad.model.Session;
import com.example.nomad.nomad.model.Ticket;

import java.util.List;

public interface TicketService {
    List<TicketDto> getTickets();
    List<ServiceModelDto> getAvailableServices(Long branchId,Long serviceId);
    List<ServiceModelDto> getChildTickets(Long serviceId);
    List<TicketDto> getAgentTickets(String agent);
    List<TicketDto> getTicketsByOpratorBranchIdAndStatus(SessionByBranchAndStatusDto session);
    List<TicketDto> getQueueTickets(Long branchId);
    List<TicketDto> getTicketsBySessionId(Long id);
    List<TicketDto> getTicketsByServiceModelId(Long id);
    List<TicketDto> getTicketsByBranchId(Long id);
    List<TicketDto> getTicketsByStatus(TicketStatus status);
    WindowDto getTicketWindow(Long id);
    TicketDto getTicketById(Long id);
    TicketStatus getTicketStatus(Long id);
    int getTicketBookingCode(Long id);

    public Session getSessionWithLeastTicketsAndService(Long branchId, Long serviceId);


    TicketDto createTicket(TicketRegisterDto newTicket);
    TicketDto bookTicket(TicketBookDto bookDto);
    TicketDto activateBookedTicket(int code);
    TicketDto callNext(SessionByBranchAndStatusDto session);
    TicketDto complete(Long id,TicketStatus status);
    TicketDto setStatus(Long id,TicketStatus status);
    TicketDto updateTicket(Long id, TicketDto newTicketBody);
    void deleteTicket(Long id);
    void setRating(Long id, int rating);
    TicketDto redirect(Long id, TicketRedirectDto ticketDto);

    Ticket getEntityById(Long id);




}
