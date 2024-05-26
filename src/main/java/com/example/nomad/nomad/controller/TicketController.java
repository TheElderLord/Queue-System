package com.example.nomad.nomad.controller;


import com.example.nomad.nomad.Enum.TicketStatus;
import com.example.nomad.nomad.dto.ServiceModelDto;
import com.example.nomad.nomad.dto.TicketDto;
import com.example.nomad.nomad.mapper.TicketMapper;
import com.example.nomad.nomad.service.ticket.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/tickets")
@AllArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @GetMapping
    private ResponseEntity<List<TicketDto>> getTickets(){
        return ResponseEntity.ok(ticketService.getTickets());
    }
    @GetMapping("/sessions/{session_id}")
    private ResponseEntity<List<TicketDto>> getBySessionAndStatus(@PathVariable Long session_id,
                                                                  @RequestParam TicketStatus status){
        return ResponseEntity.ok(ticketService.getTicketsBySessionIdAndStatus(session_id,status));
    }
    @GetMapping("/available-services/{branch_id}")
    private ResponseEntity<List<ServiceModelDto>> getAvailable(@PathVariable Long branch_id){
        List<ServiceModelDto> services = ticketService.getAvailableServices(branch_id);
        return ResponseEntity.ok(services);
    }
    @PostMapping("/register")
    private ResponseEntity<TicketDto> RegisterTicket(@RequestBody TicketDto ticketDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketService.createTicket(ticketDto));
    }
    @PostMapping("/start/{session_id}")
    private ResponseEntity<TicketDto> startServ(@PathVariable Long session_id){
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketService.callNext(session_id));
    }
    @PutMapping("/end/{id}")
    private ResponseEntity<TicketDto> stopServ(@PathVariable Long
                                                id){
        return ResponseEntity.ok(ticketService.complete(id));
    }
}
