package com.example.nomad.nomad.service.roleService.impl;

import com.example.nomad.nomad.dto.RoleServiceDto;
import com.example.nomad.nomad.exception.ResourceNotFoundException;
import com.example.nomad.nomad.mapper.RoleServiceMapper;
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

//    @Override
//    public List<RoleServiceDto> getRoleServicesByBranch(Long id) {
//        return roleServiceRepository.findAllByBranchId(id).stream()
//                .map(RoleServiceMapper::toDto).collect(Collectors.toList());
//    }


    @Override
    public void saveRoleService(Long id, RoleServiceDto[] newRoleServiceModel) {
        int length = newRoleServiceModel.length;
        Role role = roleService.getEntityById(id);
        List<RoleServiceModel> roleServiceModels = roleServiceRepository.findAllByRoleId(id);
        if(!roleServiceModels.isEmpty()){
            for (RoleServiceModel rs:roleServiceModels) {
                roleServiceRepository.deleteById(rs.getId());
            }
        }
//        RoleServiceModel[] roleServiceModelArray = new RoleServiceModel[length];
        for (int i = 0;i<length;i++) {
            ServiceModel serviceModel = servService.getEntityById(newRoleServiceModel[i].getServiceId());
            RoleServiceModel roleServiceModel = new RoleServiceModel();
            roleServiceModel.setServiceModel(serviceModel);
            roleServiceModel.setRole(role);
            roleServiceRepository.save(roleServiceModel);
        }
//        Branch branch = branchService.getEntityById(newRoleServiceModel.getBranchId());
//        roleServiceModel.setBranch(branch);

    }


    @Override
    public RoleServiceDto updateRoleService(Long id, RoleServiceDto[] newroleServiceDto) {
        RoleServiceModel roleServiceModel = getEntityById(id);
        List<RoleServiceModel> roleServiceModels = roleServiceRepository.findAllByRoleId(id);
//        roleServiceModels
//
//
////        roleServiceModel.setBranch(branch);
//        roleServiceModel.setRole(role);
//        roleServiceModel.setServiceModel(serviceModel);
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
