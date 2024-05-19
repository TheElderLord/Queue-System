package com.example.nomad.nomad.repository;

import com.example.nomad.nomad.model.ServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository  extends JpaRepository<ServiceModel,Long> {
}
