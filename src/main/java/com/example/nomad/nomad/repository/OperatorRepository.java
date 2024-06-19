package com.example.nomad.nomad.repository;

import com.example.nomad.nomad.model.Operator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OperatorRepository extends JpaRepository<Operator,Long> {

    List<Operator> findOperatorsByRoleId(Long id);
    List<Operator> findAllByActive(boolean active);
//    Operator findByLoginAndPassword(String login,String password);
    boolean existsByLogin(String login);
    Operator findByLogin(String login);
}
