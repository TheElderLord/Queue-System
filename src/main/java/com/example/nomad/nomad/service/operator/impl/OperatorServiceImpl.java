package com.example.nomad.nomad.service.operator.impl;

import com.example.nomad.nomad.Enum.TicketStatus;
import com.example.nomad.nomad.dto.OperatorDto;
import com.example.nomad.nomad.dto.operatorAuth.OperatorAuthDto;
import com.example.nomad.nomad.exception.ForbiddenActionException;
import com.example.nomad.nomad.exception.ResourceNotFoundException;
import com.example.nomad.nomad.mapper.OperatorMapper;
import com.example.nomad.nomad.model.Operator;
import com.example.nomad.nomad.model.Role;
import com.example.nomad.nomad.model.Session;
import com.example.nomad.nomad.model.Ticket;
import com.example.nomad.nomad.repository.OperatorRepository;
import com.example.nomad.nomad.repository.SessionRepository;
import com.example.nomad.nomad.repository.TicketRepository;
import com.example.nomad.nomad.service.operator.OperatorService;
import com.example.nomad.nomad.service.role.RoleService;
import com.example.nomad.nomad.service.ticket.impl.TicketServiceImpl;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Primary
@AllArgsConstructor
public class OperatorServiceImpl implements OperatorService {
    private OperatorRepository operatorRepository;
    private RoleService roleServiceService;
    private SessionRepository sessionRepository;
    private TicketRepository ticketRepository;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);
    @Override
    public List<OperatorDto> getOperators() {
        return operatorRepository.findAll().stream().map(
                OperatorMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void servCertainTicket(Long operatorId, Long ticketId) {
        Operator operator = getEntityById(operatorId);
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow();
        ticket.setOperator(operator);
        Session session = sessionRepository.findByOperatorIdAndActive(operator.getId(),true).get(0);
        ticket.setSession(session);
        ZoneId gmtPlus5 = ZoneId.of("GMT+5");
        ZonedDateTime gmtPlus5ZonedDateTime = ZonedDateTime.now(gmtPlus5);
        ticket.setServiceStartTime(gmtPlus5ZonedDateTime);
        ticket.setWindow(session.getWindow());
        ticket.setStatus(TicketStatus.INSERVICE);
        ticketRepository.save(ticket);
    }

    @Override
    public List<OperatorDto> getOnlineOperators() {
        return operatorRepository.findAllByActive(true).stream().map(
                OperatorMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<OperatorDto> getOfflineOperators() {
        return operatorRepository.findAllByActive(false).stream().map(
                OperatorMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public boolean isOperatorOnline(Long id) {
        return getEntityById(id).isActive();
    }

    @Override
    public void setActive(Long id) {
        Operator operator = getEntityById(id);
        operator.setActive(true);
        operatorRepository.save(operator);
    }
    @Override
    public void setInactive(Long id) {
        Operator operator = getEntityById(id);
        operator.setActive(false);
        operatorRepository.save(operator);
    }

    @Override
    public boolean isOperatorInDatabase(String username) {
        return operatorRepository.existsByLogin(username);
    }

    @Override
    public boolean isOperatorExist(String username) {
        return operatorRepository.existsByLogin(username);
    }

    @Override
    public OperatorDto operatorLogin(OperatorAuthDto operatorAuthDto) {
//        if(isOperatorExist(operatorAuthDto.getUsername())){

            Operator operator = operatorRepository.findByLogin(operatorAuthDto.getUsername());
            logger.info("Operator service",operator);
            if(operator ==null){
                throw new ResourceNotFoundException("Username does not exists");
            }
            if(!Objects.equals(operator.getPassword(), operatorAuthDto.getPassword())){
                throw new ForbiddenActionException("Password is invalid");
            }
            return OperatorMapper.toDto(operator);
//        }

    }

    @Override
    public OperatorDto getOperatorById(Long id) {
        return OperatorMapper.toDto(getEntityById(id));
    }

    @Override
    public Operator getOperatorByUsername(String username) {
        return operatorRepository.findByLogin(username);
    }

//    @Override
//    public Operator getByLoginAndPassword(String login, String password) {
//        Operator operator = operatorRepository.findByLoginAndPassword(login,password);
//        if(operator==null) throw new ResourceNotFoundException("Operator by login and password does not exist");
//        return operator;
//    }

    @Override
    public List<OperatorDto> getOperatorsByRoleId(Long id) {
        return operatorRepository.findOperatorsByRoleId(id).stream().map(
                OperatorMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<Operator> getActiveOperatorsFromBranchId(Long branchId) {

        return null;
    }

    @Override
    public OperatorDto createOperator(OperatorDto newOperator) {
        Operator operator = OperatorMapper.toEntity(newOperator);
        if(newOperator.getRoleId()!=null) {
            Role role = roleServiceService.getEntityById(newOperator.getRoleId());
            operator.setRole(role);
        }
        return OperatorMapper.toDto(operatorRepository.save(operator));
    }

    @Override
    public OperatorDto updateOperator(Long id, OperatorDto newOperatorBody) {
        Operator operator = getEntityById(id);
        operator.setName(newOperatorBody.getName());
        operator.setLastname(newOperatorBody.getLastname());
        if(newOperatorBody.getRoleId()!=null) {
            Role role = roleServiceService.getEntityById(newOperatorBody.getRoleId());
            operator.setRole(role);
        }
        return OperatorMapper.toDto(operatorRepository.save(operator));
    }

    @Override
    public boolean deleteOperator(Long id) {
        Operator operator = getEntityById(id);

        if(operator==null){
            return  false;
        }
        List<Session> sessions =  sessionRepository.findAllByOperatorId(operator.getId());
        List<Ticket> tickets = ticketRepository.findAllByOperatorId(operator.getId());

        for (Ticket ticket:tickets) {
            ticketRepository.deleteById(ticket.getId());
//            ticket.setOperator(null);
//            ticketRepository.save(ticket);
        }
        for (Session session:sessions) {
//                session.setOperator(null);
//                sessionRepository.save(session);
            sessionRepository.deleteById(session.getId());
        }

        operatorRepository.deleteById(id);
        return true;
    }

    @Override
    public Operator getEntityById(Long id) {
        return operatorRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("The operator does not exist")
        );
    }



}
