package com.example.nomad.nomad.repository;

import com.example.nomad.nomad.Enum.SessionStatus;
import com.example.nomad.nomad.Enum.TicketStatus;
import com.example.nomad.nomad.model.ServiceModel;
import com.example.nomad.nomad.model.Session;
import com.example.nomad.nomad.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session,Long> {
    List<Session> findAllByBranchId(Long id);
    List<Session> findAllByOperatorId(Long id);
    List<Session> findAllByWindowId(Long id);
    List<Session> findAllByActive(boolean active);

    List<Session> findAllByStatus(SessionStatus status);
}
