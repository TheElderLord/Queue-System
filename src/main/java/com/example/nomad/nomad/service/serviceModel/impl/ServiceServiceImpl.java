package com.example.nomad.nomad.service.serviceModel.impl;

import com.example.nomad.nomad.dto.ServiceModelDto;
import com.example.nomad.nomad.exception.ResourceNotFoundException;
import com.example.nomad.nomad.mapper.ServiceModelMapper;
import com.example.nomad.nomad.model.ServiceModel;
import com.example.nomad.nomad.repository.ServiceRepository;
import com.example.nomad.nomad.service.serviceModel.ServService;
import com.example.nomad.nomad.service.ticket.impl.TicketServiceImpl;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@Primary
@AllArgsConstructor
public class ServiceServiceImpl implements ServService {
    private ServiceRepository serviceRepository;

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);


    @Override
    public List<ServiceModelDto> getServices() {
        return serviceRepository.findAll().stream().map(ServiceModelMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public ServiceModelDto getServiceById(Long id) {
        return ServiceModelMapper.toDto(getEntityById(id));
    }

    @Override
    public ServiceModelDto createService(ServiceModelDto newService) {
        ServiceModel serviceModel = ServiceModelMapper.toEntity(newService);
        if(newService.getParentId()!=null){
            ServiceModel parent= getEntityById(newService.getParentId());;
            serviceModel.setParentService(parent);
        }
        return ServiceModelMapper.toDto(serviceRepository.save(serviceModel));
    }

    @Override
    public ServiceModelDto updateService(Long id, ServiceModelDto newServiceBodyModel) {
        ServiceModel service = getEntityById(id);
        if(service!=null){
            service.setName(newServiceBodyModel.getName());
            service.setDescription(newServiceBodyModel.getDescription());
            service.setPriority(newServiceBodyModel.getPriority());
            service.setMaxServTime(newServiceBodyModel.getMaxServTime());
            service.setMaxWaitTime(newServiceBodyModel.getMaxWaitTime());
            if(newServiceBodyModel.getParentId()!=null){
                ServiceModel parent= getEntityById(newServiceBodyModel.getParentId());;
                service.setParentService(parent);

            }
            return ServiceModelMapper.toDto(serviceRepository.save(service));
        }
        return null;
    }

    @Override
    public void deleteService(Long id) {
        serviceRepository.deleteById(id);
    }

    @Override
    public void setParentId(Long childId, Long parentId) {
        ServiceModel serviceModel = getEntityById(childId);
        ServiceModel parentService = getEntityById(parentId);
        if(serviceModel!=null){
            if(parentService!=null){
                serviceModel.setParentService(parentService);
                serviceRepository.save(serviceModel);
            }
        }
    }
    public ServiceModel getEntityById(Long id){
        logger.info("Searching id:" + id);
//        ServiceModel serviceModel = serviceRepository.findById(id).orElseThrow(
//                ()->new ResourceNotFoundException("The service does not exist")
//        );
        ServiceModel serviceModel = serviceRepository.findById(id).orElseThrow(null
        );
        logger.info("Found:"+serviceModel);
        return serviceModel;
    }
}
