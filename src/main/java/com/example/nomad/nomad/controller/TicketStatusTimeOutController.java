package com.example.nomad.nomad.controller;

import com.example.nomad.nomad.dto.ticket.TicketDto;
import com.example.nomad.nomad.dto.ticket.TicketStatusTimeOutDto;
import com.example.nomad.nomad.mapper.TicketMapper;
import com.example.nomad.nomad.mapper.TicketStatusTimeoutMapper;
import com.example.nomad.nomad.model.TicketStatusTimeout;
import com.example.nomad.nomad.repository.TicketStatusTimeOutRepo;
import com.example.nomad.nomad.service.ticket.impl.TicketServiceImpl;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/timeout")
@AllArgsConstructor
public class TicketStatusTimeOutController {

    private final TicketStatusTimeOutRepo ticketStatusTimeOutRepo;
    private static final Logger logger = LoggerFactory.getLogger(TicketStatusTimeOutController.class);

    @GetMapping
    public ResponseEntity<List<TicketStatusTimeOutDto>> getTimeOuts() {
        List<TicketStatusTimeOutDto> timeOuts = ticketStatusTimeOutRepo.findAll()
                .stream()
                .map(TicketStatusTimeoutMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(timeOuts);
    }

    @PostMapping
    public ResponseEntity<TicketStatusTimeOutDto> createTimeOut(@RequestBody TicketStatusTimeOutDto timeout) {
        logger.info("NEW TIME OUT: {}", timeout);
        TicketStatusTimeout ticketStatusTimeout = TicketStatusTimeoutMapper.toEntity(timeout);
        ticketStatusTimeOutRepo.save(ticketStatusTimeout);
        return ResponseEntity.ok(TicketStatusTimeoutMapper.toDto(ticketStatusTimeout));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketStatusTimeOutDto> updateTimeOut(@PathVariable Long id, @RequestBody TicketStatusTimeOutDto newTimeOut) {
        logger.info("UPDATE TIME OUT: {}", newTimeOut);
        Optional<TicketStatusTimeout> timeoutOpt = ticketStatusTimeOutRepo.findById(id);
        if (timeoutOpt.isPresent()) {
            TicketStatusTimeout timeout = timeoutOpt.get();
            timeout.setTicketStatus(newTimeOut.getFromStatus());
            timeout.setToStatus(newTimeOut.getToStatus());
            timeout.setHour(newTimeOut.getHour());
            ticketStatusTimeOutRepo.save(timeout);
            return ResponseEntity.ok(TicketStatusTimeoutMapper.toDto(timeout));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTimeOut(@PathVariable Long id) {
        if (ticketStatusTimeOutRepo.existsById(id)) {
            ticketStatusTimeOutRepo.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
