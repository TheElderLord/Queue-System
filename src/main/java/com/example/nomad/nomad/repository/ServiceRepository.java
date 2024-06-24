package com.example.nomad.nomad.repository;

import com.example.nomad.nomad.model.ServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRepository  extends JpaRepository<ServiceModel,Long> {
    List<ServiceModel> findAllByParentServiceId(Long id);
}
