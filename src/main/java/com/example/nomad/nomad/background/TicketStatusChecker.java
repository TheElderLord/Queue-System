package com.example.nomad.nomad.background;


import com.example.nomad.nomad.Enum.TicketStatus;
import com.example.nomad.nomad.dto.ticket.TicketDto;
import com.example.nomad.nomad.exception.ForbiddenActionException;
import com.example.nomad.nomad.model.Ticket;
import com.example.nomad.nomad.model.TicketStatusTimeout;
import com.example.nomad.nomad.repository.TicketStatusTimeOutRepo;
import com.example.nomad.nomad.service.ticket.TicketService;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class TicketStatusChecker {

    private final TicketService ticketService;
    private final TicketStatusTimeOutRepo ticketStatusTimeOutRepo;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SessionChecker.class);




    // Scheduled to run every minute
    @Scheduled(fixedDelay = 60000)
    public void checkSessions() {
        try {
            ZoneId gmtPlus5 = ZoneId.of("GMT+5");
            ZonedDateTime now = ZonedDateTime.now(gmtPlus5);
            TicketStatus statusNew = TicketStatus.NEW;
            TicketStatus statusInservice = TicketStatus.INSERVICE;

            List<Ticket> activeTickets = ticketService.getTicketsByStatus(Arrays.asList(statusNew,statusInservice) );
            activeTickets.stream().forEach(e->{
                Duration duration = Duration.between(e.getRegistrationTime(), now);
                if(e.getStatus() == statusNew) findStatus(statusNew, e.getId(), duration);
                else findStatus(statusInservice, e.getId(), duration);
            });

        } catch (Exception e) {
            // Handle exceptions to prevent the scheduler from halting
            e.printStackTrace();
        }
    }

    private void findStatus(TicketStatus statusNew, Long ticketId, Duration duration) {
        TicketStatusTimeout ticketStatusTimeout = ticketStatusTimeOutRepo.findByTicketStatus(statusNew);
        if(ticketStatusTimeout == null){
            throw new ForbiddenActionException("The timout is not defined");
        }
        if(duration.toHours()>=ticketStatusTimeout.getHour()){
            ticketService.complete(ticketId, ticketStatusTimeout.getToStatus());
        }

    }
}


