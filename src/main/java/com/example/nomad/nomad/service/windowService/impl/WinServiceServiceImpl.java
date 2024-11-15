package com.example.nomad.nomad.service.windowService.impl;

import com.example.nomad.nomad.dto.RoleServiceDto;
import com.example.nomad.nomad.dto.WindowServiceModelDto;
import com.example.nomad.nomad.exception.ResourceNotFoundException;
import com.example.nomad.nomad.mapper.RoleServiceMapper;
import com.example.nomad.nomad.mapper.WindowServiceMapper;
import com.example.nomad.nomad.model.*;
import com.example.nomad.nomad.repository.RoleServiceRepository;
import com.example.nomad.nomad.repository.WindowServiceRepository;
import com.example.nomad.nomad.service.branch.BranchService;
import com.example.nomad.nomad.service.role.RoleService;
import com.example.nomad.nomad.service.roleService.RoleServiceService;
import com.example.nomad.nomad.service.serviceModel.ServService;
import com.example.nomad.nomad.service.window.WindowService;
import com.example.nomad.nomad.service.windowService.WinServiceService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Primary
@AllArgsConstructor
public class WinServiceServiceImpl implements WinServiceService {
    private WindowServiceRepository windowServiceRepository;
    private RoleService roleService;
    private WindowService windowService;
    private BranchService branchService;
    private ServService servService;

    @Override
    public List<WindowServiceModelDto> getWindowServices() {
        return windowServiceRepository.findAll().stream()
                .map(WindowServiceMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public WindowServiceModelDto getWindowServiceById(Long id) {
        return WindowServiceMapper.toDto(getEntityById(id));
    }

    @Override
    public List<WindowServiceModelDto> getWindowServicesByWindowId(Long id) {
        return windowServiceRepository.findAllByWindowId(id).stream()
                .map(WindowServiceMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void saveWindowService(Long id, WindowServiceModelDto[] windowServicesModeldtos) {
        int length = windowServicesModeldtos.length;
        Window role = windowService.getEntityById(id);
        List<WindowServiceModel> roleServiceModelDtos = windowServiceRepository.findAllByWindowId(id);
        if(!roleServiceModelDtos.isEmpty()){
            for (WindowServiceModel rs: roleServiceModelDtos) {
                windowServiceRepository.deleteById(rs.getId());
            }
        }
//        RoleServiceModel[] roleServiceModelArray = new RoleServiceModel[length];
        for (int i = 0;i<length;i++) {
            ServiceModel serviceModel = servService.getEntityById(windowServicesModeldtos[i].getServiceId());
            WindowServiceModel roleServiceModelDto = new WindowServiceModel();
            roleServiceModelDto.setServiceModel(serviceModel);
            roleServiceModelDto.setWindow(role);
            windowServiceRepository.save(roleServiceModelDto);
        }
    }

    @Override
    public WindowServiceModelDto updateWindowService(Long id, WindowServiceModelDto[] WindowServiceDto) {
        WindowServiceModel roleServiceModelDto = getEntityById(id);
        List<WindowServiceModel> roleServiceModelDtos = windowServiceRepository.findAllByWindowId(id);
//        roleServiceModels
//
//
////        roleServiceModel.setBranch(branch);
//        roleServiceModel.setRole(role);
//        roleServiceModel.setServiceModel(serviceModel);
        return WindowServiceMapper.toDto(windowServiceRepository.save(roleServiceModelDto));
    }










    @Override
    public boolean deleteWindowService(Long id) {
        WindowServiceModel roleServiceModelDto = getEntityById(id);
        if (roleServiceModelDto != null) {
            windowServiceRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public WindowServiceModel getEntityById(Long id) {
        return windowServiceRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Services this role not found"));
    }



}
