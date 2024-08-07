package com.example.nomad.nomad.mapper;

import com.example.nomad.nomad.dto.ticket.TicketDto;
import com.example.nomad.nomad.model.Ticket;

public class TicketMapper {
    public static TicketDto toDto(Ticket ticket) {
        if (ticket == null) {
            return null;
        }
        TicketDto dto = new TicketDto();
        dto.setId(ticket.getId());
        dto.setTicketNumber(ticket.getTicketNumber());
        if (ticket.getServiceModel() != null) {
            dto.setServiceId(ticket.getServiceModel().getId());
            dto.setServiceName(ticket.getServiceModel().getName());
        }
        if (ticket.getSession() != null) {
            dto.setSessionId(ticket.getSession().getId());
//            dto.setWindowNum(ticket.getSession().getWindow().getNumber());
        }
        if (ticket.getWindow() != null) {
            dto.setWindowNum(ticket.getWindow().getNumber());
        }
        if (ticket.getBranch() != null) {
            dto.setBranchId(ticket.getBranch().getId());
            dto.setBranchName(ticket.getBranch().getName());
        }
        if(ticket.getOperator()!=null){
            dto.setOperatorId(ticket.getOperator().getId());
            dto.setOperatorName(ticket.getOperator().getName());
        }

        dto.setRegistrationTime(ticket.getRegistrationTime());
        dto.setServiceStartTime(ticket.getServiceStartTime());
        dto.setServiceEndTime(ticket.getServiceEndTime());
        dto.setStatus(ticket.getStatus());
//        dto.setBookingTime(ticket.getBookingTime());
//        dto.setBookingCode(ticket.getBookingCode());
        dto.setDirected(ticket.isDirected());
        if (ticket.getRedirectedWindow() != null) {
            dto.setRedirectedWindowId(ticket.getRedirectedWindow().getId());
            dto.setRedirectedWindowNumber(ticket.getRedirectedWindow().getNumber());
        }
        dto.setLanguage(ticket.getLanguage());
        dto.setAgent(ticket.getAgent());
        dto.setRating(ticket.getRating());
        dto.setComment(ticket.getComment());
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
        ticket.setServiceStartTime(dto.getServiceStartTime());
        ticket.setServiceEndTime(dto.getServiceEndTime());
        ticket.setStatus(dto.getStatus());
//        ticket.setBookingTime(dto.getBookingTime());
//        ticket.setBookingCode(dto.getBookingCode());
        ticket.setDirected(dto.isDirected());
        ticket.setAgent(dto.getAgent());
        ticket.setRating(dto.getRating());
        ticket.setLanguage(dto.getLanguage());
        ticket.setComment(dto.getComment());
        ;
        // ServiceModel, Window, Branch, and RedirectedWindow need to be set separately, typically by service layer
        return ticket;
    }
}
