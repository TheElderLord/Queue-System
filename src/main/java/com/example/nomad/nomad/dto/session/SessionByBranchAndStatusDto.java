package com.example.nomad.nomad.dto.session;

import com.example.nomad.nomad.Enum.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SessionByBranchAndStatusDto {
    private Long branchId;
    private Long operatorId;
    private TicketStatus status;
}
