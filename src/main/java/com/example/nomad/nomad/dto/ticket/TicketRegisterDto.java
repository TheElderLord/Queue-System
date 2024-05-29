package com.example.nomad.nomad.dto.ticket;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TicketRegisterDto {
    private Long serviceId;
    private Long branchId;
    private String agent;
}
