package com.example.nomad.nomad.controller;

import com.example.nomad.nomad.dto.OperatorDto;
import com.example.nomad.nomad.dto.operatorAuth.OperatorAuthDto;
import com.example.nomad.nomad.dto.operatorAuth.OperatorAuthResponse;
import com.example.nomad.nomad.dto.session.SessionByBranchAndStatusDto;
import com.example.nomad.nomad.dto.ticket.TicketDto;
import com.example.nomad.nomad.model.Operator;
//import com.example.nomad.nomad.service.operator.OperatorAuthenticationService;
import com.example.nomad.nomad.service.operator.OperatorService;
import com.example.nomad.nomad.service.ticket.TicketService;
import com.example.nomad.nomad.service.ticket.impl.TicketServiceImpl;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/operators")
@AllArgsConstructor
public class OperatorController {

    private final OperatorService operatorService;
//    private final OperatorAuthenticationService operatorAuthenticationService;
    private final TicketService ticketService;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);

    @GetMapping
    public ResponseEntity<List<OperatorDto>> getAllOperators(@RequestParam(required = false) Boolean active) {
        if(active != null){
            if(active){
                return ResponseEntity.ok(operatorService.getOnlineOperators());
            }
            else return ResponseEntity.ok(operatorService.getOfflineOperators());
        }
        return ResponseEntity.ok(operatorService.getOperators());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OperatorDto> getOperatorById(@PathVariable Long id) {
        OperatorDto operator = operatorService.getOperatorById(id);
        return ResponseEntity.ok(operator);
    }
    @PostMapping("/tickets")
    private ResponseEntity<List<TicketDto>> getByOperatorAndStatus(@RequestBody SessionByBranchAndStatusDto session){
        return ResponseEntity.ok(ticketService.getTicketsByBranchIdAndStatusAndOperator(session));
    }
    @GetMapping("/tickets/{id}")
    private ResponseEntity<List<TicketDto>> serviceSpecificTicket(@PathVariable Long id, @RequestParam(required = true) Long operatorId){
        operatorService.servCertainTicket(operatorId,id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/current/{id}")
    private ResponseEntity<List<TicketDto>> getByOperatorCurrentTickets(@PathVariable Long id){
        return ResponseEntity.ok(ticketService.getOperatorTickets(id));
    }



    @GetMapping("/role/{roleId}")
    public ResponseEntity<List<OperatorDto>> getOperatorsByRoleId(@PathVariable Long roleId) {
        List<OperatorDto> operators = operatorService.getOperatorsByRoleId(roleId);
        return ResponseEntity.ok(operators);
    }

    @PostMapping
    public ResponseEntity<OperatorDto> createOperator(@RequestBody OperatorDto newOperator) {
        OperatorDto createdOperator = operatorService.createOperator(newOperator);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOperator);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OperatorDto> updateOperator(@PathVariable Long id, @RequestBody OperatorDto updatedOperator) {
        logger.info(id+":"+updatedOperator);
        OperatorDto operator = operatorService.updateOperator(id, updatedOperator);
        return ResponseEntity.ok(operator);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOperator(@PathVariable Long id) {
        boolean isDeleted = operatorService.deleteOperator(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @PostMapping("/login")
    public ResponseEntity<OperatorDto> operatorLogin(@RequestBody OperatorAuthDto operator) {
        logger.debug("Operator Controller",operator);
        var isOk =  operatorService.operatorLogin(operator);
        return ResponseEntity.ok(isOk);
    }
}
