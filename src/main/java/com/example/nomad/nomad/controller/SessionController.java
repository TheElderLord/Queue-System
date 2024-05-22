package com.example.nomad.nomad.controller;

import com.example.nomad.nomad.dto.ServiceModelDto;
import com.example.nomad.nomad.dto.SessionDto;
import com.example.nomad.nomad.model.Session;
import com.example.nomad.nomad.service.session.SessionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sessions")
@AllArgsConstructor
public class SessionController {
    private final SessionService service;

    @GetMapping
    private ResponseEntity<List<SessionDto>> getSessions(){
        return ResponseEntity.ok(service.getSessions());
    }
    @GetMapping("/available")
    private ResponseEntity<List<ServiceModelDto>> getAvailable(){
        List<ServiceModelDto> services = service.getAvailableServices();
        return ResponseEntity.ok(services);
    }
    @PostMapping
    private ResponseEntity<SessionDto> createASession(@RequestBody SessionDto sessionDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.startASession(sessionDto));
    }

}
