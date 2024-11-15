package com.example.nomad.nomad.service.window.impl;

import com.example.nomad.nomad.dto.RoleDto;
import com.example.nomad.nomad.dto.RoleServiceDto;
import com.example.nomad.nomad.dto.WindowDto;
import com.example.nomad.nomad.dto.WindowServiceModelDto;
import com.example.nomad.nomad.exception.ResourceNotFoundException;
import com.example.nomad.nomad.mapper.RoleMapper;
import com.example.nomad.nomad.mapper.RoleServiceMapper;
import com.example.nomad.nomad.mapper.WindowMapper;
import com.example.nomad.nomad.mapper.WindowServiceMapper;
import com.example.nomad.nomad.model.Branch;
import com.example.nomad.nomad.model.ServiceModel;
import com.example.nomad.nomad.model.Window;
import com.example.nomad.nomad.repository.WindowRepository;
import com.example.nomad.nomad.repository.WindowServiceRepository;
import com.example.nomad.nomad.service.branch.BranchService;
import com.example.nomad.nomad.service.serviceModel.ServService;
import com.example.nomad.nomad.service.session.SessionService;
import com.example.nomad.nomad.service.window.WindowService;
import com.example.nomad.nomad.service.windowService.WinServiceService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Primary
@AllArgsConstructor
public class WindowServiceImpl implements WindowService {
    private final WindowRepository windowRepository;
    private final WindowServiceRepository windowServiceRepository;
    private ServService servService;

    private final BranchService branchService;
//    private final SessionService sessionService;

    @Override
    public List<WindowDto> getWindows() {
        List<WindowDto> roles = windowRepository.findAll().stream()
                .map(WindowMapper::toDto)
                .toList();

        List<WindowDto> roleDtos = roles.stream()
                .map(e -> {
                    var roleServices = windowServiceRepository.findAllByWindowId(e.getId()).stream()
                            .map(WindowServiceMapper::toDto)
                            .collect(Collectors.toList());;
                    e.setWindowServiceModelDtos(roleServices);
                    return e;
                })
                .collect(Collectors.toList());

        for (WindowDto windowDto:roleDtos) {
            for (WindowServiceModelDto windowServiceModelDto:windowDto.getWindowServiceModelDtos()) {
                ServiceModel serviceModel = servService.getEntityById(windowServiceModelDto.getServiceId());
                windowServiceModelDto.setParentServiceName(serviceModel.getParentService().getName());
            }
        }

        return roleDtos;
    }

    @Override
    public WindowDto getWindowById(Long id) {
        Window window = getEntityById(id);
        return WindowMapper.toDto(window);
    }

    @Override
    public boolean isWindowActive(Long id) {
        return getEntityById(id).isActive();
    }

    @Override
    public void setActive(Long id) {
        Window window = getEntityById(id);
        window.setActive(true);
        windowRepository.save(window);
    }

    @Override
    public void setInactive(Long id) {
        Window window = getEntityById(id);
        window.setActive(false);
        windowRepository.save(window);
    }

    @Override
    public List<WindowDto> getActiveWindows() {
        return windowRepository.findAllByActive(true).stream()
                .map(WindowMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<WindowDto> getInactiveWindows() {
        return windowRepository.findAllByActive(false).stream()
                .map(WindowMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<WindowDto> getWindowsByBranchId(Long id,Boolean active) {
        if(active!=null){
            if(!active){
                return windowRepository.findAllByBranchIdAndActive(id,active).stream()
                        .map(WindowMapper::toDto)
                        .collect(Collectors.toList());
            }
        }
        return windowRepository.findAllByBranchId(id).stream()
                .map(WindowMapper::toDto)
                .collect(Collectors.toList());
    }

//    @Override
//    public List<WindowDto> getActiveWindowsByOperatorId(Long id) {
//        return ;
//    }

    @Override
    public WindowDto createWindow(WindowDto newWindow) {
        Window window = WindowMapper.toEntity(newWindow);
        Branch branch = branchService.getEntityById(newWindow.getBranchId());
        window.setBranch(branch);
        return WindowMapper.toDto(windowRepository.save(window));
    }

    @Override
    public WindowDto updateWindow(Long id, WindowDto windowDto) {
        Window window = getEntityById(id);
        window.setName(windowDto.getName());
        window.setDescription(window.getDescription());
        Branch branch = branchService.getEntityById(windowDto.getBranchId());
        window.setBranch(branch);
        return WindowMapper.toDto(windowRepository.save(window));
    }

    @Override
    public void deleteWindow(Long id) {
        windowRepository.deleteById(id);
    }

    @Override
    public Window getEntityById(Long id) {
        return windowRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Window not found with id " + id));
    }
}
