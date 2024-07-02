package com.example.nomad.nomad.dto.ticket;

import com.example.nomad.nomad.Enum.Language;
import com.example.nomad.nomad.Enum.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
public class TicketDto {
    private Long id;
    private int ticketNumber;
    private Long serviceId;
    private String serviceName;
    private Long sessionId;
    private Long windowNum;
    private Long branchId;
    private Long operatorId;
    private String operatorName;
    private String branchName;
    private ZonedDateTime registrationTime;
    private ZonedDateTime serviceStartTime;
    private ZonedDateTime serviceEndTime;
    private TicketStatus status;
    private ZonedDateTime bookingTime;
    private int bookingCode;
    private boolean directed;
    private Long redirectedWindowId;
    private Long redirectedWindowNumber;
    private String agent;
    private int rating;
    private String comment;
    private Language language;
}
