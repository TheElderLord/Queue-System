package com.example.nomad.nomad.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@NoArgsConstructor
public class RoleDto {
    private Long id;
    private String name;
    private String description;
    private Long priority;
    private List<RoleServiceDto> roleServices;
}
