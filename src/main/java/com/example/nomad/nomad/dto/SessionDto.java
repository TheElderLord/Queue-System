package com.example.nomad.nomad.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class SessionDto {
    private Long id;
    @NonNull
    private String status;
    @NonNull
    private LocalDate startTime;
    @NonNull
    private LocalDate endTime;
    private Long operatorId;
    private Long windowId;
    private Long branchId;
}
