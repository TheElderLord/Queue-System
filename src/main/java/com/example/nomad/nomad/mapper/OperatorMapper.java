package com.example.nomad.nomad.mapper;

import com.example.nomad.nomad.dto.OperatorDto;
import com.example.nomad.nomad.model.Operator;

public class OperatorMapper {
    public static OperatorDto toDto(Operator operator) {
        if (operator == null) {
            return null;
        }
        OperatorDto dto = new OperatorDto();
        dto.setId(operator.getId());
        dto.setName(operator.getName());
        dto.setLastname(operator.getLastname());
        dto.setLogin(operator.getLogin());
        dto.setPassword(operator.getPassword());
        dto.setActive(operator.isActive());
        if (operator.getRole() != null) {
            dto.setRoleId(operator.getRole().getId());
            dto.setRoleName(operator.getRole().getName());
        }
        return dto;
    }

    public static Operator toEntity(OperatorDto dto) {
        if (dto == null) {
            return null;
        }
        Operator operator = new Operator();
        operator.setId(dto.getId());
        operator.setName(dto.getName());
        operator.setLastname(dto.getLastname());
        operator.setLogin(dto.getLogin());
        operator.setPassword(dto.getPassword());
        operator.setActive(dto.isActive());
        // Role needs to be set separately, typically by service layer
        return operator;
    }
}
