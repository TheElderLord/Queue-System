package com.example.nomad.nomad.service.serviceModel;

import com.example.nomad.nomad.dto.ServiceModelDto;
import com.example.nomad.nomad.model.ServiceModel;

import java.util.List;

public interface ServService {
    List<ServiceModelDto> getServices();
    List<ServiceModelDto> getChildService(Long id);

    ServiceModelDto getServiceById(Long id);

    ServiceModelDto createService(ServiceModelDto serviceModel);

    ServiceModelDto updateService(Long id, ServiceModelDto newServiceBodyModel);

    void deleteService(Long id);

    void setParentId(Long childId,Long parentId);

    ServiceModel getEntityById(Long id);

}
