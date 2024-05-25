package com.example.nomad.nomad.dto;

import com.example.nomad.nomad.Enum.SessionStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class SessionDto {
    private Long id;
    private SessionStatus status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean active;
    private Long operatorId;
    private String operatorName;
    private Long windowId;
    private Long windowNumber;
    private String windowName;
    private Long branchId;
    private String branchName;
}
