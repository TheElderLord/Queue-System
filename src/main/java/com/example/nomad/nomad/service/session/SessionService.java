package com.example.nomad.nomad.service.session;

import com.example.nomad.nomad.Enum.SessionStatus;
import com.example.nomad.nomad.dto.session.SessionDto;
import com.example.nomad.nomad.model.Session;

import java.util.List;

public interface SessionService {

    //Get
    List<SessionDto> getSessions();
    List<SessionDto> getSessionsByBranchId(Long id);
    List<SessionDto> getSessionsByOperatorId(Long id);
    List<SessionDto> getSessionsByWindowId(Long id);
    List<SessionDto> getActiveSessions();
    List<SessionDto> getInActiveSessions();
    SessionDto getSessionById(Long id);
    List<SessionDto> getSessionsByStatus(SessionStatus sessionStatus);
    List<Session> getActiveSessionsByBranchId(Long branchId,boolean active);

//    Session getSessionWithLeastTicketsAndService(Long brachId,Long serviceId);

    boolean isSessionActive(Long id);


    //Get

    SessionDto startASession(SessionDto newSession);
    SessionDto stopASession(Long id);
    SessionDto updateSession(Long id, SessionDto newSessionBody);

    void deleteSession(Long id);



    Session getEntityById(Long id);
}
