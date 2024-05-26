package com.example.nomad.nomad.repository;

import com.example.nomad.nomad.Enum.SessionStatus;
import com.example.nomad.nomad.Enum.TicketStatus;
import com.example.nomad.nomad.model.Session;
import com.example.nomad.nomad.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
    List<Ticket> findAllBySessionId(Long id);
    List<Ticket> findAllByBranchId(Long id);
    List<Ticket> findAllByServiceModelId(Long id);
    List<Ticket> findAllByStatus(TicketStatus status);
    List<Ticket> findAllBySessionIdAndStatus(Long session_id, TicketStatus status);

}
