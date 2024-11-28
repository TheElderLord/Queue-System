package com.example.nomad.nomad.repository;

import com.example.nomad.nomad.Enum.TicketStatus;
import com.example.nomad.nomad.model.Ticket;
import com.example.nomad.nomad.model.TicketStatusTimeout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketStatusTimeOutRepo extends JpaRepository<TicketStatusTimeout,Long> {
    TicketStatusTimeout findByTicketStatus(TicketStatus ticketStatus);
}
