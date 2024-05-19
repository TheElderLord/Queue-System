package com.example.nomad.nomad.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class ServiceModelDto {
    private Long id;
    @NonNull
    private String name;
    private String description;
    @NonNull
    private Long maxServTime;
    @NonNull
    private Long maxWaitTime;
    @NonNull
    private Long priority;
    private Long parentId;
}
