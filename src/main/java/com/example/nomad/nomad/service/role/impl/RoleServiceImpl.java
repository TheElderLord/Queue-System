package com.example.nomad.nomad.service.role.impl;


import com.example.nomad.nomad.dto.RoleDto;
import com.example.nomad.nomad.mapper.RoleMapper;
import com.example.nomad.nomad.model.Role;
import com.example.nomad.nomad.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Primary
@AllArgsConstructor
public class RoleServiceImpl implements com.example.nomad.nomad.service.role.RoleService {
    private RoleRepository roleRepository;

    @Override
    public List<RoleDto> getAllRoles() {
        return roleRepository.findAll().stream().map(RoleMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public RoleDto saveRole(RoleDto role) {
        return RoleMapper.toDto(roleRepository.save(RoleMapper.toEntity(role)));
    }

    @Override
    public RoleDto getRoleById(Long id) {
        return RoleMapper.toDto(getEntityById(id));
    }

    @Override
    public RoleDto updateRole(Long id, RoleDto updatedRole) {
        // Retrieve the existing role from the database
        Role existingRole = getEntityById(id);

        // Update the properties of the existing role with the values from the updatedRole object

        existingRole.setName(updatedRole.getName());
        existingRole.setDescription(updatedRole.getDescription());
        existingRole.setPriority(updatedRole.getPriority());

        // Save the updated role
        return RoleMapper.toDto(roleRepository.save(existingRole));

    }

    @Override
    public void deleteRole(Long id) {
        Role role = getEntityById(id);
        roleRepository.deleteById(role.getId());

    }

    public Role getEntityById(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new ResolutionException("Role does not exist"));
    }


}