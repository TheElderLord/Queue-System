package com.example.nomad.nomad.controller;

import com.example.nomad.nomad.dto.OperatorDto;
import com.example.nomad.nomad.service.operator.OperatorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/operators")
@AllArgsConstructor
public class OperatorController {

    private final OperatorService operatorService;

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
}
