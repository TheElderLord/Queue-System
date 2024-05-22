package com.example.nomad.nomad.service.roleService.impl;

import com.example.nomad.nomad.dto.BranchDto;
import com.example.nomad.nomad.dto.RoleDto;
import com.example.nomad.nomad.dto.RoleServiceDto;
import com.example.nomad.nomad.dto.ServiceModelDto;
import com.example.nomad.nomad.exception.ResourceNotFoundException;
import com.example.nomad.nomad.mapper.BranchMapper;
import com.example.nomad.nomad.mapper.RoleMapper;
import com.example.nomad.nomad.mapper.RoleServiceMapper;
import com.example.nomad.nomad.mapper.ServiceModelMapper;
import com.example.nomad.nomad.model.Branch;
import com.example.nomad.nomad.model.Role;
import com.example.nomad.nomad.model.RoleServiceModel;
import com.example.nomad.nomad.model.ServiceModel;
import com.example.nomad.nomad.repository.RoleServiceRepository;
import com.example.nomad.nomad.service.branch.BranchService;
import com.example.nomad.nomad.service.role.RoleService;
import com.example.nomad.nomad.service.roleService.RoleServiceService;
import com.example.nomad.nomad.service.serviceModel.ServService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Primary
@AllArgsConstructor
public class RoleServiceServiceImpl implements RoleServiceService {
    private RoleServiceRepository roleServiceRepository;
    private RoleService roleService;
    private BranchService branchService;
    private ServService servService;

    @Override
    public List<RoleServiceDto> getRoleServices() {
        return roleServiceRepository.findAll().stream()
                .map(RoleServiceMapper::toDto).collect(Collectors.toList());
    }

//    @Override
//    public List<RoleServiceDto> getRoleServicesById(Long id) {
//        return roleServiceRepository.findAllById(id).stream()
//                .map(RoleServiceMapper::toDto).collect(Collectors.toList());
//    }

    @Override
    public RoleServiceDto getRoleServiceById(Long id) {
        return RoleServiceMapper.toDto(getEntityById(id));
    }

    @Override
    public List<RoleServiceDto> getRoleServicesByRoleId(Long id) {
        return roleServiceRepository.findAllByRoleId(id).stream()
                .map(RoleServiceMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<RoleServiceDto> getRoleServicesByBranch(Long id) {
        return roleServiceRepository.findAllByBranchId(id).stream()
                .map(RoleServiceMapper::toDto).collect(Collectors.toList());
    }


    @Override
    public RoleServiceDto saveRoleService(RoleServiceDto newRoleServiceModel) {
        RoleServiceModel roleServiceModel = RoleServiceMapper.toEntity(newRoleServiceModel);

        Branch branch = branchService.getEntityById(newRoleServiceModel.getBranchId());
        ServiceModel serviceModel = servService.getEntityById(newRoleServiceModel.getServiceId());
        Role role = roleService.getEntityById(newRoleServiceModel.getRoleId());

        roleServiceModel.setServiceModel(serviceModel);
        roleServiceModel.setBranch(branch);
        roleServiceModel.setRole(role);

        return RoleServiceMapper.toDto(roleServiceRepository.save(roleServiceModel));
    }


    @Override
    public RoleServiceDto updateRoleService(Long id, RoleServiceDto newroleServiceDto) {
        RoleServiceModel roleServiceModel = getEntityById(id);

        Branch branch = branchService.getEntityById(newroleServiceDto.getBranchId());
        ServiceModel serviceModel = servService.getEntityById(newroleServiceDto.getServiceId());
        Role role = roleService.getEntityById(newroleServiceDto.getRoleId());

        roleServiceModel.setBranch(branch);
        roleServiceModel.setRole(role);
        roleServiceModel.setServiceModel(serviceModel);
        return RoleServiceMapper.toDto(roleServiceRepository.save(roleServiceModel));


    }

    @Override
    public boolean deleteRoleService(Long id) {
        RoleServiceModel roleServiceModel = getEntityById(id);
        if (roleServiceModel != null) {
            roleServiceRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public RoleServiceModel getEntityById(Long id) {
        return roleServiceRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Services this role not found"));
    }

}
