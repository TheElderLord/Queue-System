package com.example.nomad.nomad.controller;


import com.example.nomad.nomad.dto.RoleDto;
import com.example.nomad.nomad.service.role.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@AllArgsConstructor
public class RoleController {


    @Qualifier("roleServiceImpl")
    private final RoleService roleService;

    @GetMapping
    private ResponseEntity<List<RoleDto>> findAllRoles() {
        List<RoleDto> list = roleService.getAllRoles();
        return ResponseEntity.ok(list);
    }
    @GetMapping("/{requestedId}")
    private ResponseEntity<RoleDto> findRoleById(@PathVariable Long requestedId){
        RoleDto requestedRole = roleService.getRoleById(requestedId);
        if(requestedRole==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(requestedRole);
    }

    @PostMapping
    private ResponseEntity<RoleDto> createRole(@RequestBody RoleDto newRole){
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.saveRole(newRole));
    }


    @PutMapping("/{id}")
    private ResponseEntity<RoleDto> updateRole(@PathVariable Long id, @RequestBody RoleDto role){
        return ResponseEntity.ok(roleService.updateRole(id,role));
    }
    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteRole(@PathVariable Long id){
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }

}
