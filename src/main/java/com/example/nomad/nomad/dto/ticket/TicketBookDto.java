package com.example.nomad.nomad.dto.ticket;

import com.example.nomad.nomad.Enum.TerminalType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
public class TicketBookDto {
    private Long serviceId;
    private Long branchId;
    private String agent;
//    private TerminalType terminalType;
    private ZonedDateTime bookingTime;
}

