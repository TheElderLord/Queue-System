package com.example.nomad.nomad.dto.session;

import com.example.nomad.nomad.Enum.SessionStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
public class SessionDto {
    private Long id;
    private SessionStatus status;
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
    private boolean active;
    private Long operatorId;
    private String operatorName;
    private Long windowId;
    private Long windowNumber;
    private String windowName;
    private Long branchId;
    private String branchName;
}
