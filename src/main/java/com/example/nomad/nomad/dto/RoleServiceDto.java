package com.example.nomad.nomad.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class RoleServiceDto {
    private Long id;
    @NonNull
    private Long branchId;
    @NonNull
    private Long roleId;
    @NonNull
    private Long serviceId;
}
