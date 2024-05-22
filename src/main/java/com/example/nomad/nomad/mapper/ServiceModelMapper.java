package com.example.nomad.nomad.mapper;

import com.example.nomad.nomad.dto.ServiceModelDto;
import com.example.nomad.nomad.model.ServiceModel;

public class ServiceModelMapper {
    public static ServiceModelDto toDto(ServiceModel service) {
        if (service == null) {
            return null;
        }
        ServiceModelDto dto = new ServiceModelDto();
        dto.setId(service.getId());
        dto.setName(service.getName());
        dto.setDescription(service.getDescription());
//        dto.setMaxServTime(service.getMaxServTime());
//        dto.setMaxWaitTime(service.getMaxWaitTime());
        dto.setPriority(service.getPriority());
        if (service.getParentService() != null) {
            dto.setParentId(service.getParentService().getId());
        }
        return dto;
    }

    public static ServiceModel toEntity(ServiceModelDto dto) {
        if (dto == null) {
            return null;
        }
        ServiceModel service = new ServiceModel();
        service.setId(dto.getId());
        service.setName(dto.getName());
        service.setDescription(dto.getDescription());
//        service.setMaxServTime(dto.getMaxServTime());
//        service.setMaxWaitTime(dto.getMaxWaitTime());
        service.setPriority(dto.getPriority());
        // Parent service model needs to be set separately, typically by service layer
        return service;
    }
}
