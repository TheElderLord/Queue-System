package com.example.nomad.nomad.controller;


import com.example.nomad.nomad.Enum.TicketStatus;
import com.example.nomad.nomad.dto.ServiceModelDto;
import com.example.nomad.nomad.dto.session.SessionByBranchAndStatusDto;
import com.example.nomad.nomad.dto.ticket.TicketDto;
import com.example.nomad.nomad.dto.ticket.TicketRegisterDto;
import com.example.nomad.nomad.service.ticket.TicketService;
import com.example.nomad.nomad.service.ticket.impl.TicketServiceImpl;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tickets")
@AllArgsConstructor
public class TicketController {
    private final TicketService ticketService;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);

    @GetMapping
    private ResponseEntity<List<TicketDto>> getTickets(){
        return ResponseEntity.ok(ticketService.getTickets());
    }
    @GetMapping("/sessions")
    private ResponseEntity<List<TicketDto>> getBySessionAndStatus(@RequestBody SessionByBranchAndStatusDto session){
        return ResponseEntity.ok(ticketService.getTicketsBySessionBranchIdAndStatus(session));
    }
    @GetMapping("/agent/{agent}")
    private ResponseEntity<List<TicketDto>> getByAgentTickets(@PathVariable String agent){
        return ResponseEntity.ok(ticketService.getAgentTickets(agent));
    }
    @GetMapping("/available-services/{branch_id}")
    private ResponseEntity<List<ServiceModelDto>> getAvailable(@PathVariable Long branch_id){
        List<ServiceModelDto> services = ticketService.getAvailableServices(branch_id);
        return ResponseEntity.ok(services);
    }
    @PostMapping("/register")
    private ResponseEntity<TicketDto> RegisterTicket(@RequestBody TicketRegisterDto ticketDto){
        logger.info(String.valueOf(ticketDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketService.createTicket(ticketDto));
    }
    @PostMapping("/start")
    private ResponseEntity<TicketDto> startServ(@RequestBody SessionByBranchAndStatusDto session){
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketService.callNext(session));
    }
    @PutMapping("/end/{id}")
    private ResponseEntity<TicketDto> stopServ(@PathVariable Long
                                                id){
        return ResponseEntity.ok(ticketService.complete(id));
    }
}
