package com.example.nomad.nomad.repository;

import com.example.nomad.nomad.dto.RoleServiceDto;
import com.example.nomad.nomad.model.Role;
import com.example.nomad.nomad.model.RoleServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleServiceRepository extends JpaRepository<RoleServiceModel,Long> {

//    List<RoleServiceModel> findAllById(Long id);
    List<RoleServiceModel> findAllByRoleId(Long id);


}
