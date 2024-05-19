package com.example.nomad.nomad.service.session;

import com.example.nomad.nomad.dto.ServiceModelDto;
import com.example.nomad.nomad.dto.SessionDto;
import com.example.nomad.nomad.model.Session;

import java.util.List;

public interface SessionService {

    //Get
    List<SessionDto> getSessions();
    List<SessionDto> getSessionsByBranchId(Long id);
    List<SessionDto> getSessionsByOperatorId(Long id);
    List<SessionDto> getSessionByWindowId(Long id);
    List<SessionDto> getActiveSessions();
    List<SessionDto> getInActiveSessions();
    boolean isSessionActive(Long id);
    SessionDto getSessionById(Long id);

    List<ServiceModelDto> getAvailableServices();
    //Get

    //Simple Post Put
    SessionDto createSession(SessionDto newSession);
    SessionDto updateSession(Long id, SessionDto newSessionBody);
    void deleteSession(Long id);
    //Simple Post Put



    Session getEntityById(Long id);
}
