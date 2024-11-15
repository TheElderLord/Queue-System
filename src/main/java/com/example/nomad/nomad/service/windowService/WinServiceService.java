package com.example.nomad.nomad.service.windowService;

import com.example.nomad.nomad.dto.WindowServiceModelDto;
import com.example.nomad.nomad.model.WindowServiceModel;

import java.util.List;

public interface WinServiceService {

    List<WindowServiceModelDto> getWindowServices();

//    List<WindowServiceDto> getWindowServicesById(Long id);

    WindowServiceModelDto getWindowServiceById(Long id);

    List<WindowServiceModelDto> getWindowServicesByWindowId(Long id);
//    List<WindowServiceDto> getWindowServicesByBranch(Long id);

    void saveWindowService(Long id, WindowServiceModelDto[] WindowServiceDto);
    WindowServiceModelDto updateWindowService(Long id, WindowServiceModelDto[] WindowServiceDto);
    boolean deleteWindowService(Long id);

    WindowServiceModel getEntityById(Long id);


}
