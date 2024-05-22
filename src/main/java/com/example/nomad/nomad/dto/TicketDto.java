package com.example.nomad.nomad.dto;

import com.example.nomad.nomad.Enum.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class TicketDto {
    private Long id;
//    private int ticketNumber;
    private Long serviceId;
    private Long sessionId;
    private Long branchId;
//    private LocalDate registrationTime;
//    private LocalDate serviceStartTime;
//    private LocalDate serviceEndTime;
//    @NonNull
    private TicketStatus status;
    private LocalDate bookingTime;
    private int bookingCode;
//    private boolean isDirected;
    private Long redirectedWindowId;
    private String agent;
    private int rating;
}
