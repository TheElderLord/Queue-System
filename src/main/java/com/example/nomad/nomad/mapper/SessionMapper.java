package com.example.nomad.nomad.mapper;

import com.example.nomad.nomad.dto.SessionDto;
import com.example.nomad.nomad.model.Session;

public class SessionMapper {
    public static SessionDto toDto(Session session) {
        if (session == null) {
            return null;
        }
        SessionDto dto = new SessionDto();
        dto.setId(session.getId());
        dto.setStatus(session.getStatus());
        dto.setStartTime(session.getStartTime());
        dto.setEndTime(session.getEndTime());
        dto.setActive(session.isActive());
        if (session.getOperator() != null) {
            dto.setOperatorId(session.getOperator().getId());
        }
        if (session.getWindow() != null) {
            dto.setWindowId(session.getWindow().getId());
        }
        if (session.getBranch() != null) {
            dto.setBranchId(session.getBranch().getId());
        }
        return dto;
    }

    public static Session toEntity(SessionDto dto) {
        if (dto == null) {
            return null;
        }
        Session session = new Session();
        session.setId(dto.getId());
        session.setStatus(dto.getStatus());
        session.setStartTime(dto.getStartTime());
        session.setEndTime(dto.getEndTime());
        session.setActive(dto.isActive());
        // Operator, Window, and Branch need to be set separately, typically by service layer
        return session;
    }
}
