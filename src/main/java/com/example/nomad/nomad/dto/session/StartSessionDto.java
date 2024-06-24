package com.example.nomad.nomad.dto.session;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StartSessionDto {
    private Long operatorId;
    private Long windowId;
    private Long branchId;
}
