package com.example.nomad.nomad.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class RoleServiceDto {
    private Long id;
    private Long branchId;
    private Long roleId;
    private Long serviceId;
}
