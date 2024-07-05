package com.example.nomad.nomad.service.session.impl;

import com.example.nomad.nomad.Enum.SessionStatus;
import com.example.nomad.nomad.dto.session.SessionDto;
import com.example.nomad.nomad.dto.session.StartSessionDto;
import com.example.nomad.nomad.exception.ForbiddenActionException;
import com.example.nomad.nomad.exception.ResourceNotFoundException;
import com.example.nomad.nomad.mapper.SessionMapper;
import com.example.nomad.nomad.model.*;
import com.example.nomad.nomad.repository.SessionRepository;
import com.example.nomad.nomad.repository.TicketRepository;
import com.example.nomad.nomad.service.branch.BranchService;
import com.example.nomad.nomad.service.operator.OperatorService;
import com.example.nomad.nomad.service.session.SessionService;
import com.example.nomad.nomad.service.window.WindowService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Primary
@AllArgsConstructor
public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepository;

    private final OperatorService operatorService;

    private final BranchService branchService;
    private final WindowService windowService;
    private final TicketRepository ticketRepository;

    //GET METHODS
    @Override
    public List<SessionDto> getSessions() {
        return sessionRepository.findAll().stream().map(SessionMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<SessionDto> getSessionsByBranchId(Long id) {
        return sessionRepository.findAllByBranchId(id).stream().map(SessionMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<SessionDto> getSessionsByOperatorId(Long id) {
        return sessionRepository.findAllByOperatorId(id).stream().map(SessionMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<SessionDto> getSessionsByOperatorIdAndStatus(Long id) {
        return sessionRepository.findByOperatorIdAndActive(id,true).stream().map(SessionMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<SessionDto> getSessionsByWindowId(Long id) {
        return sessionRepository.findAllByWindowId(id).stream().map(SessionMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<SessionDto> getActiveSessions() {
        return sessionRepository.findAllByActive(true).stream().map(SessionMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<SessionDto> getInActiveSessions() {
        return sessionRepository.findAllByActive(false).stream().map(SessionMapper::toDto).collect(Collectors.toList());

    }

    @Override
    public SessionDto getSessionById(Long id) {
        return SessionMapper.toDto(getEntityById(id));
    }

    @Override
    public List<SessionDto> getSessionsByStatus(SessionStatus sessionStatus) {
        return sessionRepository.findAllByStatus(sessionStatus).stream().map(SessionMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<Session> getActiveSessionsByBranchId(Long branchId, boolean active) {
        return sessionRepository.findAllByBranchIdAndActive(branchId,active);
    }




    @Override
    public boolean isSessionActive(Long id) {
        return getEntityById(id).isActive();
    }



    //GET METHODS


    @Override
    public SessionDto startASession(StartSessionDto newSession) {
        Session session = new Session();

        Branch branch = branchService.getEntityById(newSession.getBranchId());
        Operator operator = operatorService.getEntityById(newSession.getOperatorId());
        List<Session> activeOperatorSessions = sessionRepository.findByOperatorIdAndActive(operator.getId(),true);
        if(!activeOperatorSessions.isEmpty()){
           throw  new ForbiddenActionException("Operator already has a active session");
        }
        Window window = windowService.getEntityById(newSession.getWindowId());

        windowService.setActive(newSession.getWindowId());
        operatorService.setActive(newSession.getOperatorId());
        session.setActive(true);
        session.setStatus(SessionStatus.ONNLINE);
        ZoneId gmtPlus5 = ZoneId.of("GMT+5");
        ZonedDateTime gmtPlus5ZonedDateTime = ZonedDateTime.now(gmtPlus5);
        session.setStartTime(gmtPlus5ZonedDateTime);
        session.setBranch(branch);
        session.setOperator(operator);
        session.setWindow(window);
        sessionRepository.save(session);
        return SessionMapper.toDto(session);
    }

    @Override
    public SessionDto stopASession(Long id,SessionStatus sessionStatus) {
        Session session = getEntityById(id);

//        if(!session.isActive()){
//            throw new ForbiddenActionException("The session is already finished");
//        }
        windowService.setInactive(session.getWindow().getId());
        operatorService.setInactive(session.getOperator().getId());
        session.setStatus(sessionStatus);
        ZoneId gmtPlus5 = ZoneId.of("GMT+5");
        ZonedDateTime gmtPlus5ZonedDateTime = ZonedDateTime.now(gmtPlus5);
        session.setEndTime(gmtPlus5ZonedDateTime);
        session.setActive(false);

        sessionRepository.save(session);
        return null;
    }



    @Override
    public SessionDto updateSession(Long id, SessionDto newSessionBody) {
        Session session = getEntityById(id);
        Branch branch = branchService.getEntityById(newSessionBody.getBranchId());
        Operator operator = operatorService.getEntityById(newSessionBody.getOperatorId());
        Window window = windowService.getEntityById(newSessionBody.getWindowId());

        session.setBranch(branch);
        session.setOperator(operator);
        session.setWindow(window);
        return SessionMapper.toDto(session);
    }

    @Override
    public void deleteSession(Long id) {
        Session session = getEntityById(id);
        List<Ticket> tickets = ticketRepository.findAllBySessionId(session.getId());
//        ticketRepository.deleteAllBySessionId(id);
        for (Ticket ticket:tickets) {

            ticketRepository.deleteById(ticket.getId());
        }
        sessionRepository.deleteById(id);
    }




    @Override
    public Session getEntityById(Long id) {
        return sessionRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("The session does not exist")
        );
    }
}
