package com.example.nomad.nomad.repository;

import com.example.nomad.nomad.Enum.TicketStatus;
import com.example.nomad.nomad.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
    List<Ticket> getAllBySessionId(Long id);
    List<Ticket> getAllByBranchId(Long id);
    List<Ticket> getAllByServiceModelId(Long id);
    List<Ticket> getAllByStatus(TicketStatus status);
}
