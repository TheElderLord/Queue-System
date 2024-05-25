package com.example.nomad.nomad.controller;

import com.example.nomad.nomad.Enum.SessionStatus;
import com.example.nomad.nomad.dto.ServiceModelDto;
import com.example.nomad.nomad.dto.SessionDto;
import com.example.nomad.nomad.service.session.SessionService;
import com.example.nomad.nomad.service.session.impl.SessionServiceImpl;
import com.example.nomad.nomad.service.ticket.impl.TicketServiceImpl;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sessions")
@AllArgsConstructor
public class SessionController {
    private final SessionService service;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);



    @GetMapping
    private ResponseEntity<List<SessionDto>> getSessions(@RequestParam(required = false) SessionStatus sessionStatus,
                                                         @RequestParam(required = false) Boolean active){

//        logger.info(String.valueOf(sessionStatus));
        if(sessionStatus !=null){
//            logger.info(String.valueOf("Not null:"+sessionStatus));
            return ResponseEntity.ok(service.getSessionsByStatus(sessionStatus));
        }
        if(active !=null){
            if (active){
                return ResponseEntity.ok(service.getActiveSessions());
            }
            return ResponseEntity.ok(service.getInActiveSessions());
        }
        return ResponseEntity.ok(service.getSessions());
    }
    @GetMapping("/{id}")
    ResponseEntity<SessionDto> getSessionById(@PathVariable Long id){
        return ResponseEntity.ok(service.getSessionById(id));
    }
    @GetMapping("/branch/{id}")
    ResponseEntity<List<SessionDto>> getSessionByBranchId(@PathVariable Long id){
        return ResponseEntity.ok(service.getSessionsByBranchId(id));
    }
    @GetMapping("/operator/{id}")
    ResponseEntity<List<SessionDto>> getSessionByOperatorId(@PathVariable Long id){
        return ResponseEntity.ok(service.getSessionsByOperatorId(id));
    }
    @GetMapping("/window/{id}")
    ResponseEntity<List<SessionDto>> getSessionByWindowId(@PathVariable Long id){
        return ResponseEntity.ok(service.getSessionsByWindowId(id));
    }

//    @GetMapping("/active")
//    private ResponseEntity<List<SessionDto>> getActiveSessions(){
//        return ResponseEntity.ok(service.getActiveSessions());
//    }
//    @GetMapping("/inactive")
//    private ResponseEntity<List<SessionDto>> getInActiveSessions(){
//        return ResponseEntity.ok(service.getInActiveSessions());
//    }
//    @GetMapping("?status={status}")
//    private ResponseEntity<List<SessionDto>> getSessionsByStatus(@PatchMapping)
    @PostMapping()
    private ResponseEntity<SessionDto> createASession(@RequestBody SessionDto sessionDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.startASession(sessionDto));
    }
    @PutMapping("/{id}")
    private ResponseEntity<SessionDto> stopASession(@PathVariable Long id,@RequestParam SessionStatus sessionStatus){
        logger.info(String.valueOf(sessionStatus));
        return ResponseEntity.status(HttpStatus.CREATED).body(service.stopASession(id,sessionStatus));
    }


    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteASession(@PathVariable Long id){
        service.deleteSession(id);
        return ResponseEntity.noContent().build();
    }

}
