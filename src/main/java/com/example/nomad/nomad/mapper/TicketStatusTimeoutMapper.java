package com.example.nomad.nomad.mapper;


import com.example.nomad.nomad.dto.ticket.TicketStatusTimeOutDto;
import com.example.nomad.nomad.model.TicketStatusTimeout;

public class TicketStatusTimeoutMapper {

    public static TicketStatusTimeOutDto toDto(TicketStatusTimeout entity) {
        if (entity == null) {
            return null;
        }
        TicketStatusTimeOutDto dto = new TicketStatusTimeOutDto();
        dto.id = entity.getId();
        dto.fromStatus = entity.getTicketStatus();
        dto.hour = entity.getHour();
        dto.toStatus = entity.getToStatus();
        return dto;
    }

    public static TicketStatusTimeout toEntity(TicketStatusTimeOutDto dto) {
        if (dto == null) {
            return null;
        }
        TicketStatusTimeout entity = new TicketStatusTimeout();
        entity.setId(dto.id);
        entity.setTicketStatus(dto.fromStatus);
        entity.setHour(dto.hour);
        entity.setToStatus(dto.toStatus);
        return entity;
    }
}

