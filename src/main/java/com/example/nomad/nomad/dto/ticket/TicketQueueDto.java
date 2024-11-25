package com.example.nomad.nomad.dto.ticket;


import com.example.nomad.nomad.Enum.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketQueueDto {
    private Long id;
    private Long windowNum;
    private int ticketNumber;
    private Language language;
    private ZonedDateTime RegistrationTime;
}
