package com.example.nomad.nomad.mapper;

import com.example.nomad.nomad.dto.RoleServiceDto;
import com.example.nomad.nomad.dto.WindowServiceModelDto;
import com.example.nomad.nomad.model.WindowServiceModel;

public class WindowServiceMapper {
    public static com.example.nomad.nomad.dto.WindowServiceModelDto toDto(WindowServiceModel windowService) {
        if (windowService == null) {
            return null;
        }
        com.example.nomad.nomad.dto.WindowServiceModelDto dto = new com.example.nomad.nomad.dto.WindowServiceModelDto();
        dto.setId(windowService.getId());
//        if (windowService.getBranch() != null) {
//            dto.setBranchId(windowService.getBranch().getId());
//            dto.setBranchName(windowService.getBranch().getName());
//
//        }
        if (windowService.getWindow() != null) {
            dto.setWindowId(windowService.getWindow().getId());
//            dto.setRoleName(windowService.getRole().getName());
        }
        if (windowService.getServiceModel() != null) {
            dto.setServiceId(windowService.getServiceModel().getId());
            dto.setServiceName(windowService.getServiceModel().getName());
        }
        return dto;
    }

    public static WindowServiceModel toEntity(WindowServiceModelDto dto) {
        if (dto == null) {
            return null;
        }
        WindowServiceModel roleService = new WindowServiceModel();
        roleService.setId(dto.getId());
        // Branch, Role, and ServiceModel need to be set separately, typically by service layer
        return roleService;
    }
}
