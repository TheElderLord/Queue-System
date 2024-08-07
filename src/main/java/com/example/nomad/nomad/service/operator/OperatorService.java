package com.example.nomad.nomad.service.operator;

import com.example.nomad.nomad.dto.OperatorDto;
import com.example.nomad.nomad.dto.operatorAuth.OperatorAuthDto;
import com.example.nomad.nomad.model.Operator;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface OperatorService {
    List<OperatorDto> getOperators();
    void  servCertainTicket(Long operatorId,Long ticketId);
    List<OperatorDto> getOnlineOperators();
    List<OperatorDto> getOfflineOperators();
    List<OperatorDto> getOperatorsByRoleId(Long id);
    List<Operator> getActiveOperatorsFromBranchId(Long branchId);
    OperatorDto getOperatorById(Long id);
    Operator getOperatorByUsername(String username);
//    Operator getByLoginAndPassword(String login, String password);
    OperatorDto createOperator(OperatorDto newOperator);
    OperatorDto updateOperator(Long id,OperatorDto newOperatorBody);
    boolean deleteOperator(Long id);
    boolean isOperatorOnline(Long id);
    void setActive(Long id);
    void setInactive(Long id);

    boolean isOperatorInDatabase(String username);
    boolean isOperatorExist(String username);

    OperatorDto operatorLogin(OperatorAuthDto operatorAuthDto);
    Operator getEntityById(Long id);


}
