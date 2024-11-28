package com.example.nomad.nomad.controller;

import com.example.nomad.nomad.dto.ticket.TicketDto;
import com.example.nomad.nomad.model.TicketStatusTimeout;
import com.example.nomad.nomad.repository.TicketStatusTimeOutRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/timeout")
@AllArgsConstructor
public class TicketStatusTimeOutController {
    private final TicketStatusTimeOutRepo ticketStatusTimeOutRepo;

    @GetMapping
    private ResponseEntity<List<TicketStatusTimeout>> getTimeOuts(){
        return ResponseEntity.ok(ticketStatusTimeOutRepo.findAll());
    }
    @PostMapping
    private ResponseEntity<TicketStatusTimeout> createTimeOut(@RequestBody TicketStatusTimeout timeout){
        ticketStatusTimeOutRepo.save(timeout);
        return ResponseEntity.ok(timeout);
    }
    @PutMapping("/id")
    private ResponseEntity<TicketStatusTimeout> updateTimeOut(@PathVariable Long id, @RequestBody TicketStatusTimeout newTimeOut){
        Optional<TicketStatusTimeout> timeout = ticketStatusTimeOutRepo.findById(id);
        if(timeout.isPresent()){
            timeout.get().setTicketStatus(newTimeOut.getTicketStatus());
            timeout.get().setToStatus(newTimeOut.getToStatus());
            timeout.get().setHour(newTimeOut.getHour());
            ticketStatusTimeOutRepo.save(timeout.get());
            return ResponseEntity.ok(timeout.get());
        }
        return ResponseEntity.internalServerError().build();

    }
}
