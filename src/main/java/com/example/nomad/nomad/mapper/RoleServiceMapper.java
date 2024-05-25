package com.example.nomad.nomad.mapper;

import com.example.nomad.nomad.dto.RoleServiceDto;
import com.example.nomad.nomad.model.RoleServiceModel;

public class RoleServiceMapper {
    public static RoleServiceDto toDto(RoleServiceModel roleService) {
        if (roleService == null) {
            return null;
        }
        RoleServiceDto dto = new RoleServiceDto();
        dto.setId(roleService.getId());
        if (roleService.getBranch() != null) {
            dto.setBranchId(roleService.getBranch().getId());
            dto.setBranchName(roleService.getBranch().getName());

        }
        if (roleService.getRole() != null) {
            dto.setRoleId(roleService.getRole().getId());
            dto.setRoleName(roleService.getRole().getName());
        }
        if (roleService.getServiceModel() != null) {
            dto.setServiceId(roleService.getServiceModel().getId());
            dto.setServiceName(roleService.getServiceModel().getName());
        }
        return dto;
    }

    public static RoleServiceModel toEntity(RoleServiceDto dto) {
        if (dto == null) {
            return null;
        }
        RoleServiceModel roleService = new RoleServiceModel();
        roleService.setId(dto.getId());
        // Branch, Role, and ServiceModel need to be set separately, typically by service layer
        return roleService;
    }
}
