package com.example.nomad.nomad.mapper;

import com.example.nomad.nomad.dto.ticket.TicketDto;
import com.example.nomad.nomad.dto.ticket.TicketQueueDto;
import com.example.nomad.nomad.model.Ticket;

public class TicketQueueMapper {
    public static TicketQueueDto toDto(Ticket ticket) {
        if (ticket == null) {
            return null;
        }
        TicketQueueDto dto = new TicketQueueDto();
        dto.setId(ticket.getId());
        dto.setTicketNumber(ticket.getTicketNumber());
        if (ticket.getWindow() != null) {
            dto.setWindowNum(ticket.getWindow().getNumber());
        }

        dto.setRegistrationTime(ticket.getRegistrationTime());

        dto.setLanguage(ticket.getLanguage());
        return dto;
    }

    public static Ticket toEntity(TicketDto dto) {
        if (dto == null) {
            return null;
        }
        Ticket ticket = new Ticket();
        ticket.setId(dto.getId());
        ticket.setTicketNumber(dto.getTicketNumber());
        ticket.setRegistrationTime(dto.getRegistrationTime());
        ticket.setLanguage(dto.getLanguage());
        return ticket;
    }
}
