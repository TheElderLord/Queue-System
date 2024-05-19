package com.example.nomad.nomad.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class WindowDto {
    private Long id;
    private Long branchId;
    @NonNull
    private String status;
    @NonNull
    private String name;
    private String description;
}
