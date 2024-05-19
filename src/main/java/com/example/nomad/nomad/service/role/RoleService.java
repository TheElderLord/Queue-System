package com.example.nomad.nomad.service.role;

import com.example.nomad.nomad.dto.RoleDto;
import com.example.nomad.nomad.model.Role;

import java.util.List;

public interface RoleService {

    List<RoleDto> getAllRoles();
    RoleDto getRoleById(Long id);

    RoleDto saveRole(RoleDto role);
    RoleDto updateRole(Long id, RoleDto student);
    void deleteRole(Long id);

     Role getEntityById(Long id);


}
