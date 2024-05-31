package com.example.nomad.nomad.dto.ticket;

import com.example.nomad.nomad.Enum.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private String branchName;
    private LocalDateTime registrationTime;
    private LocalDateTime serviceStartTime;
    private LocalDateTime serviceEndTime;
    private TicketStatus status;
    private LocalDateTime bookingTime;
    private Long bookingCode;
    private boolean directed;
    private Long redirectedWindowId;
    private Long redirectedWindowNumber;
    private String agent;
    private int rating;
}
