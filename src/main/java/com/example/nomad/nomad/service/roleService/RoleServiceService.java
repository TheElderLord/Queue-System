package com.example.nomad.nomad.service.roleService;

import com.example.nomad.nomad.dto.RoleServiceDto;
import com.example.nomad.nomad.model.RoleServiceModel;

import java.util.List;

public interface RoleServiceService {

    List<RoleServiceDto> getRoleServices();

    List<RoleServiceDto> getRoleServicesById(Long id);

    RoleServiceDto getRoleServiceById(Long id);

    List<RoleServiceDto> getRoleServicesByRole(Long id);

    RoleServiceDto saveRoleService(RoleServiceDto roleServiceDto);
    RoleServiceDto updateRoleService(Long id, RoleServiceDto roleServiceDto);
    boolean deleteRoleService(Long id);

    RoleServiceModel getEntityById(Long id);


}
