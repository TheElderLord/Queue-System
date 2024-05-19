package com.example.nomad.nomad.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class TicketDto {
    private String id;
    private int ticketNumber;
    private Long serviceId;
    private Long windowId;
    private Long branchId;
    private LocalDate registrationTime;
    private LocalDate serviceStartTime;
    private LocalDate serviceEndTime;
    @NonNull
    private String status;
    private LocalDate bookingTime;
    private int bookingCode;
    @NonNull
    private boolean isDirected;
    private Long redirectedWindowId;
    private String agent;
    private int rating;
}
