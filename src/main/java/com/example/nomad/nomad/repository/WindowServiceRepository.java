package com.example.nomad.nomad.repository;

import com.example.nomad.nomad.dto.WindowServiceModelDto;
import com.example.nomad.nomad.model.RoleServiceModel;
import com.example.nomad.nomad.model.WindowServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WindowServiceRepository extends JpaRepository<WindowServiceModel,Long> {

    //    List<RoleServiceModel> findAllById(Long id);
    List<WindowServiceModel> findAllByWindowId(Long id);
    List<WindowServiceModel> findAllByServiceModelId(Long id);

}