package com.example.nomad.nomad.dto.ticket;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketQueueDto {
    private Long windowId;
    private Long ticketNumber;
}
