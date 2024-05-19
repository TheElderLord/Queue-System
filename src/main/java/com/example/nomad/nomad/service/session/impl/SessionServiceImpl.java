package com.example.nomad.nomad.service.session.impl;

import com.example.nomad.nomad.dto.*;
import com.example.nomad.nomad.exception.ResourceNotFoundException;
import com.example.nomad.nomad.mapper.SessionMapper;
import com.example.nomad.nomad.model.*;
import com.example.nomad.nomad.repository.SessionRepository;
import com.example.nomad.nomad.service.branch.BranchService;
import com.example.nomad.nomad.service.operator.OperatorService;
import com.example.nomad.nomad.service.role.RoleService;
import com.example.nomad.nomad.service.roleService.RoleServiceService;
import com.example.nomad.nomad.service.serviceModel.ServService;
import com.example.nomad.nomad.service.session.SessionService;
import com.example.nomad.nomad.service.window.WindowService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Primary
@AllArgsConstructor
public class SessionServiceImpl implements SessionService {
    private SessionRepository sessionRepository;
    private OperatorService operatorService;
    private RoleServiceService roleServiceService;
    private ServService servService;
    private BranchService branchService;
    private WindowService windowService;
    @Override
    public List<SessionDto> getSessions() {
        return sessionRepository.findAll().stream().map(SessionMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<SessionDto> getSessionsByBranchId(Long id) {
        return sessionRepository.findAllByBranchId(id).stream().map(SessionMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<SessionDto> getSessionsByOperatorId(Long id) {
        return sessionRepository.findAllByOperatorId(id).stream().map(SessionMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<SessionDto> getSessionByWindowId(Long id) {
        return sessionRepository.findAllByWindowId(id).stream().map(SessionMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<SessionDto> getActiveSessions() {
        return sessionRepository.findAllByActive(true).stream().map(SessionMapper::toDto).collect(Collectors.toList());

    }

    @Override
    public List<SessionDto> getInActiveSessions() {
        return sessionRepository.findAllByActive(false).stream().map(SessionMapper::toDto).collect(Collectors.toList());

    }

    @Override
    public boolean isSessionActive(Long id) {
        return getEntityById(id).isActive();
    }

    @Override
    public SessionDto getSessionById(Long id) {
        return SessionMapper.toDto(getEntityById(id));
    }

    @Override
    public List<ServiceModelDto> getAvailableServices() {
        // Get a list of active operators
        List<OperatorDto> activeOperators = operatorService.getOnlineOperators();

        // If no active operators, return an empty list of services
        if (activeOperators.isEmpty()) {
            return Collections.emptyList();
        }

        // Extract the IDs of roles of active operators
        List<Long> roleIds = activeOperators.stream()
                .map(OperatorDto::getRoleId)
                .distinct()  // Ensure distinct role IDs
                .toList();

        // Fetch available services based on the role IDs
        List<Long> availableServiceIds = roleIds.stream()
                .flatMap(roleId -> roleServiceService.getRoleServicesByRoleId(roleId).stream())
                .distinct()
                .map(RoleServiceDto::getServiceId)// Ensure distinct services
                .toList();

        // Extract service models from available services
        List<ServiceModelDto> serviceModels = availableServiceIds.stream()
                .map(serviceId->servService.getServiceById(serviceId))
                .distinct()  // Ensure distinct service models
                .collect(Collectors.toList());

        return serviceModels;
    }


    @Override
    public SessionDto createSession(SessionDto newSession) {
        Session session = SessionMapper.toEntity(newSession);
        Branch branch = branchService.getEntityById(newSession.getBranchId());
        Operator operator = operatorService.getEntityById(newSession.getOperatorId());
        Window window = windowService.getEntityById(newSession.getWindowId());

        session.setBranch(branch);
        session.setOperator(operator);
        session.setWindow(window);
        return SessionMapper.toDto(session);
    }

    @Override
    public SessionDto updateSession(Long id, SessionDto newSessionBody) {
        Session session = getEntityById(id);
        Branch branch = branchService.getEntityById(newSessionBody.getBranchId());
        Operator operator = operatorService.getEntityById(newSessionBody.getOperatorId());
        Window window = windowService.getEntityById(newSessionBody.getWindowId());

        session.setBranch(branch);
        session.setOperator(operator);
        session.setWindow(window);
        return SessionMapper.toDto(session);
    }

    @Override
    public void deleteSession(Long id) {
        sessionRepository.deleteById(id);
    }

    @Override
    public Session getEntityById(Long id) {
        return sessionRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("The service does not exist")
        );
    }
}
