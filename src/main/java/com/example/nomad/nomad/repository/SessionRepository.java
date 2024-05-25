package com.example.nomad.nomad.repository;

import com.example.nomad.nomad.Enum.SessionStatus;
import com.example.nomad.nomad.Enum.TicketStatus;
import com.example.nomad.nomad.model.ServiceModel;
import com.example.nomad.nomad.model.Session;
import com.example.nomad.nomad.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session,Long> {
    List<Session> findAllByBranchId(Long id);
    List<Session> findAllByOperatorId(Long id);
    List<Session> findByOperatorIdAndActive(Long id,boolean active);

    List<Session> findAllByWindowId(Long id);
    List<Session> findAllByActive(boolean active);
    List<Session> findAllByStatus(SessionStatus status);

    @Query("SELECT s FROM Session s WHERE s.id = (SELECT t.session.id FROM Ticket t GROUP BY t.session.id ORDER BY COUNT(t) ASC LIMIT 1)")
    Session findSessionWithLeastTickets();

    @Query("SELECT t.session FROM Ticket t WHERE t.serviceModel.id = :serviceModelId GROUP BY t.session ORDER BY COUNT(t.id) ASC")
    List<Session> findSessionWithLeastTicketsByServiceModel(@Param("serviceModelId") Long serviceModelId);

    @Query("SELECT s FROM Session s WHERE s.branch.id = :branchId AND s.service.id = :serviceId")
    List<Session> findAllByBranchIdAndServiceId(@Param("branchId") Long branchId, @Param("serviceId") Long serviceId);

}
