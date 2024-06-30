package com.example.nomad.nomad.repository;

import com.example.nomad.nomad.Enum.TicketStatus;
import com.example.nomad.nomad.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
    List<Ticket> findAllBySessionId(Long id);
    List<Ticket> findAllByBranchId(Long id);
    List<Ticket> findAllByOperatorId(Long id);
    List<Ticket> findAllByServiceModelId(Long id);
    List<Ticket> findAllByStatus(TicketStatus status);
    List<Ticket> findAllByAgent(String agent);
    List<Ticket> findAllByAgentAndStatus(String agent,TicketStatus status);
    List<Ticket> findAllByOperatorIdAndBranchIdAndStatus(Long session_id,Long branch_id ,TicketStatus status );
    List<Ticket> findAllByBranchIdAndStatus(Long branchId,TicketStatus status);
    boolean existsByTicketNumber(int ticketNumber);
    boolean existsByBookingCode(int bookingCode);
    void deleteAllByOperatorId(Long id);
    void deleteAllBySessionId(Long id);
    void deleteById(Long id);
    Ticket findByBookingCode(int bookCode);
}
