package com.example.nomad.nomad.service.window;

import com.example.nomad.nomad.dto.WindowDto;
import com.example.nomad.nomad.model.Window;

import java.util.List;

public interface WindowService {

    List<WindowDto> getWindows();
    WindowDto getWindowById(Long id);
    boolean isWindowActive(Long id);
    List<WindowDto> getActiveWindows();
    List<WindowDto> getInactiveWindows();
    List<WindowDto> getWindowsByBranchId(Long id);
    WindowDto createWindow(WindowDto newWindow);
    WindowDto updateWindow(Long id, WindowDto windowDto);
    void deleteWindow(Long id);

    Window getEntityById(Long id);

}
