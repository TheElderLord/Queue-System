package com.example.nomad.nomad.repository;

import com.example.nomad.nomad.model.Window;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WindowRepository extends JpaRepository<Window,Long> {

    List<Window> findAllByBranchId(Long id);
    List<Window> findAllByActive(boolean active);
    List<Window> findAllByBranchIdAndActive(Long id,Boolean active);
}
