package com.example.nomad.nomad.service.operator;

import com.example.nomad.nomad.dto.OperatorDto;
import com.example.nomad.nomad.model.Operator;

import java.util.List;

public interface OperatorService {
    List<OperatorDto> getOperators();
    List<OperatorDto> getOnlineOperators();
    List<OperatorDto> getOfflineOperators();
    List<OperatorDto> getOperatorsByRoleId(Long id);
    List<Operator> getActiveOperatorsFromBranchId(Long branchId);
    OperatorDto getOperatorById(Long id);
    Operator getByLoginAndPassword(String login, String password);
    OperatorDto createOperator(OperatorDto newOperator);
    OperatorDto updateOperator(Long id,OperatorDto newOperatorBody);
    boolean deleteOperator(Long id);
    boolean isOperatorOnline(Long id);
    void setActive(Long id);
    void setInactive(Long id);

    Operator getEntityById(Long id);

}
