package com.example.nomad.nomad.dto.ticket;


import com.example.nomad.nomad.Enum.Language;
import com.example.nomad.nomad.Enum.TerminalType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TicketRegisterDto {
    private Long serviceId;
    private Long branchId;
    private String agent;
    private TerminalType terminalType;
    private Language language;
}
