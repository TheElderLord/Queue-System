package com.example.nomad.nomad.mapper;

import com.example.nomad.nomad.dto.WindowDto;
import com.example.nomad.nomad.model.Window;

public class WindowMapper {
    public static WindowDto toDto(Window window) {
        if (window == null) {
            return null;
        }
        WindowDto dto = new WindowDto();
        dto.setId(window.getId());
        if (window.getBranch() != null) {
            dto.setBranchId(window.getBranch().getId());
        }
        dto.setStatus(window.getStatus());
        dto.setName(window.getName());
        dto.setDescription(window.getDescription());
        dto.setActive(window.isActive());
        return dto;
    }

    public static Window toEntity(WindowDto dto) {
        if (dto == null) {
            return null;
        }
        Window window = new Window();
        window.setId(dto.getId());
        window.setStatus(dto.getStatus());
        window.setName(dto.getName());
        window.setDescription(dto.getDescription());
        window.setActive(dto.isActive());
        // Branch needs to be set separately, typically by service layer
        return window;
    }
}
