package com.example.nomad.nomad.background;


import com.example.nomad.nomad.Enum.TicketStatus;
import com.example.nomad.nomad.dto.ticket.TicketDto;
import com.example.nomad.nomad.model.Ticket;
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
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SessionChecker.class);




    // Scheduled to run every minute
    @Scheduled(fixedDelay = 60000)
    public void checkSessions() {
        try {
            ZoneId gmtPlus5 = ZoneId.of("GMT+5");
            ZonedDateTime now = ZonedDateTime.now(gmtPlus5);

            // Fetch all active sessions
            List<Ticket> activeTickets = ticketService.getTicketsByStatus(Arrays.asList(TicketStatus.NEW, TicketStatus.INSERVICE) );

            for (Ticket ticket : activeTickets) {
                Duration duration = Duration.between(ticket.getRegistrationTime(), now);

                if (duration.toHours() >= 3) {
                    // Stop the session
                    ticketService.complete(ticket.getId(), TicketStatus.MISSED);
                    // Log the action if needed
                    logger.info("Ticket with ID " + ticket.getId() + " has been stopped after 8 hours.");
                }
            }
        } catch (Exception e) {
            // Handle exceptions to prevent the scheduler from halting
            e.printStackTrace();
        }
    }
}


