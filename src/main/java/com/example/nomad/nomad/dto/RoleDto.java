package com.example.nomad.nomad.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class RoleDto {
    private Long id;
    private String name;
    private String description;
    private String priority;
}
