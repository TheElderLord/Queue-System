package com.example.nomad.nomad.dto.ticket;

import com.example.nomad.nomad.Enum.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TicketStatusTimeOutDto {
    public Long id;
    public TicketStatus fromStatus;
    public int hour;
    public TicketStatus toStatus;
}
