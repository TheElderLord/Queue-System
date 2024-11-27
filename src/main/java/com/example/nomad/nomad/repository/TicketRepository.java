package com.example.nomad.nomad.repository;

import com.example.nomad.nomad.Enum.TicketStatus;
import com.example.nomad.nomad.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
    List<Ticket> findAllBySessionId(Long id);
    List<Ticket> findAllByBranchId(Long id);
    List<Ticket> findAllByOperatorId(Long id);
    List<Ticket> findAllByOperatorIdAndStatus(Long id,TicketStatus status);
    List<Ticket> findAllByServiceModelId(Long id);
    List<Ticket> findAllByServiceModelIdAndStatus(Long id,TicketStatus status);
    List<Ticket> findAllByStatus(TicketStatus status);
    List<Ticket> findAllByStatusIn(Collection<TicketStatus> status);
    List<Ticket> findAllByAgent(String agent);
    List<Ticket> findAllByAgentAndStatus(String agent,TicketStatus status);
    @Query("SELECT t FROM Ticket t WHERE t.agent = :agent AND t.status <> :status")
    List<Ticket> findAllByAgentAndStatusNot(@Param("agent") String agent, @Param("status") TicketStatus status);

    List<Ticket> findAllByOperatorIdAndBranchIdAndStatus(Long session_id,Long branch_id ,TicketStatus status );
    List<Ticket> findAllByBranchIdAndStatus(Long branchId,TicketStatus status);
    List<Ticket> findAllByBranchIdAndStatusAndRegistrationTimeBetween(Long branchId,TicketStatus status,ZonedDateTime startOfDay, ZonedDateTime endOfDay);
    List<Ticket> findAllByBranchIdAndRegistrationTimeBetween(Long branchId, ZonedDateTime startOfDay, ZonedDateTime endOfDay);

    @Query("SELECT t FROM Ticket t WHERE t.status = :status AND t.registrationTime < :time")
    List<Ticket> findAllByStatusAndRegistrationTimeBefore(@Param("status") TicketStatus status, @Param("time") ZonedDateTime time);

    boolean existsByTicketNumber(int ticketNumber);
    boolean existsByBookingCode(int bookingCode);
    void deleteAllByOperatorId(Long id);
    void deleteAllBySessionId(Long id);
    void deleteById(Long id);
    Ticket findByBookingCode(int bookCode);
}
