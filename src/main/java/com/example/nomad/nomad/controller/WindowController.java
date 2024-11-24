package com.example.nomad.nomad.controller;

import com.example.nomad.nomad.dto.WindowDto;
import com.example.nomad.nomad.service.window.WindowService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/windows")
@AllArgsConstructor
public class WindowController {

    @Qualifier("windowServiceImpl")
    private final WindowService windowService;

    @GetMapping
    public ResponseEntity<List<WindowDto>> getWindows() {
        List<WindowDto> windows = windowService.getWindows();
        return ResponseEntity.ok(windows);
    }
    @GetMapping("/active")
    public ResponseEntity<List<WindowDto>> getActiveWindows() {
        List<WindowDto> windows = windowService.getActiveWindows();
        return ResponseEntity.ok(windows);
    }
    @GetMapping("/inactive")
    public ResponseEntity<List<WindowDto>> getInactiveWindows() {
        List<WindowDto> windows = windowService.getInactiveWindows();
        return ResponseEntity.ok(windows);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WindowDto> getWindowById(@PathVariable Long id) {
        WindowDto window = windowService.getWindowById(id);
        return ResponseEntity.ok(window);
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<WindowDto>> getWindowsByBranchId(@PathVariable Long branchId,@RequestParam(required = false) Boolean active) {
        List<WindowDto> windows = windowService.getWindowsByBranchId(branchId,active);
        if (windows.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(windows);
    }

    @PostMapping
    public ResponseEntity<WindowDto> createWindow(@RequestBody WindowDto newWindow) {
        WindowDto createdWindow = windowService.createWindow(newWindow);
        return ResponseEntity.status(HttpStatus.CREATED).body((createdWindow));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WindowDto> updateWindow(@PathVariable Long id, @RequestBody WindowDto updatedWindow) {
        WindowDto window = windowService.updateWindow(id, updatedWindow);
        return ResponseEntity.ok(window);
    }
    @PutMapping("/disable/{windowId}")
    public ResponseEntity<Void> deactivateWindow(@PathVariable Long windowId){
        windowService.setInactive(windowId);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWindow(@PathVariable Long id) {
        windowService.deleteWindow(id);
        return ResponseEntity.noContent().build();
    }

}
