package com.example.nomad.nomad.service.window;

import com.example.nomad.nomad.dto.WindowDto;
import com.example.nomad.nomad.model.Window;

import java.time.LocalDate;
import java.util.List;

public interface WindowService {

    List<WindowDto> getWindows();
    List<WindowDto> getActiveWindows();
    List<WindowDto> getInactiveWindows();
    List<WindowDto> getWindowsByBranchId(Long id,Boolean active);
//    List<WindowDto> getActiveWindowsByOperatorId(Long id);
    WindowDto createWindow(WindowDto newWindow);
    WindowDto updateWindow(Long id, WindowDto windowDto);
    WindowDto getWindowById(Long id);

    boolean isWindowActive(Long id);

    void setActive(Long id);
    void setInactive(Long id);
    void deleteWindow(Long id);


    Window getEntityById(Long id);

}
