package com.example.nomad.nomad.dto;

import com.example.nomad.nomad.Enum.SessionStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class SessionDto {
    private Long id;
    private SessionStatus status;
    private boolean isActive;
    private Long operatorId;
    private Long windowId;
    private Long branchId;
}
