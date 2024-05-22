package com.example.nomad.nomad.controller;


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
    @PostMapping
    private ResponseEntity<TicketDto> createTicket(@RequestBody TicketDto ticketDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketService.createTicket(ticketDto));
    }
}
