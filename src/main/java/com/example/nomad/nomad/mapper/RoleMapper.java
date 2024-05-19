package com.example.nomad.nomad.mapper;

import com.example.nomad.nomad.dto.RoleDto;
import com.example.nomad.nomad.model.Role;

public class RoleMapper {
    public static RoleDto toDto(Role role) {
        if (role == null) {
            return null;
        }
        RoleDto dto = new RoleDto();
        dto.setId(role.getId());
        dto.setName(role.getName());
        dto.setDescription(role.getDescription());
        dto.setPriority(role.getPriority());
        return dto;
    }

    public static Role toEntity(RoleDto dto) {
        if (dto == null) {
            return null;
        }
        Role role = new Role();
        role.setId(dto.getId());
        role.setName(dto.getName());
        role.setDescription(dto.getDescription());
        role.setPriority(dto.getPriority());
        return role;
    }
}
