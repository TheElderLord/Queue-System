package com.example.nomad.nomad.controller;


import com.example.nomad.nomad.Enum.TicketStatus;
import com.example.nomad.nomad.dto.ServiceModelDto;
import com.example.nomad.nomad.dto.session.SessionByBranchAndStatusDto;
import com.example.nomad.nomad.dto.ticket.TicketDto;
import com.example.nomad.nomad.dto.ticket.TicketRedirectDto;
import com.example.nomad.nomad.dto.ticket.TicketRegisterDto;
import com.example.nomad.nomad.service.ticket.TicketService;
import com.example.nomad.nomad.service.ticket.impl.TicketServiceImpl;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tickets")
@AllArgsConstructor
public class TicketController {
    private final TicketService ticketService;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping
    private ResponseEntity<List<TicketDto>> getTickets(@RequestParam(required = false) String agent,
                                                       @RequestParam(required = false) String status){

        return ResponseEntity.ok(ticketService.getTickets());
    }
    @GetMapping("/{id}")
    private ResponseEntity<TicketDto> getTicketById(@PathVariable Long id){
        return ResponseEntity.ok(ticketService.getTicketById(id));
    }
    @PostMapping("/sessions")
    private ResponseEntity<List<TicketDto>> getByOperatorAndStatus(@RequestBody SessionByBranchAndStatusDto session){
        return ResponseEntity.ok(ticketService.getTicketsByOpratorBranchIdAndStatus(session));
    }
    @GetMapping("/agent/{agent}")
    private ResponseEntity<List<TicketDto>> getByAgentTickets(@PathVariable String agent){
        return ResponseEntity.ok(ticketService.getAgentTickets(agent));
    }
    @GetMapping("/queue")
    private ResponseEntity<List<TicketDto>> getByStatusTickets(@RequestParam(required = true) Long branchId){
        return ResponseEntity.ok(ticketService.getQueueTickets(branchId));
    }
    @GetMapping("/available-services/{branch_id}")
    private ResponseEntity<List<ServiceModelDto>> getAvailable(@PathVariable Long branch_id,@RequestParam(required = false) Long serviceId){
        if(serviceId!=null){
            return ResponseEntity.ok(ticketService.getChildTickets(serviceId));
        }
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
        TicketDto ticket = ticketService.callNext(session);
        messagingTemplate.convertAndSend("/topic/tickets", ticket);
        return ResponseEntity.status(HttpStatus.CREATED).body(ticket);
    }
    @PostMapping("/redirect/{id}")
    private ResponseEntity<TicketDto> redirectTicket(@PathVariable Long id, @RequestBody TicketRedirectDto ticketDto){
        TicketDto ticket = ticketService.redirect(id,ticketDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ticket);
    }
    @PutMapping("/end/{id}")
    private ResponseEntity<TicketDto> stopServ(@PathVariable Long
                                                id, @RequestParam TicketStatus status){
        return ResponseEntity.ok(ticketService.complete(id,status));
    }
    @PutMapping("/rating/{id}")
    private ResponseEntity<Void> setRate(@PathVariable Long id,@RequestParam(required = true) int rating){
        ticketService.setRating(id,rating);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteTicket(@PathVariable Long id){
        ticketService.deleteTicket(id);
        return ResponseEntity.ok().build();
    }

}
