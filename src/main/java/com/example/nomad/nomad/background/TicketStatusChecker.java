package com.example.nomad.nomad.background;



import com.example.nomad.nomad.Enum.SessionStatus;
import com.example.nomad.nomad.Enum.TicketStatus;
import com.example.nomad.nomad.dto.ticket.TicketDto;
import com.example.nomad.nomad.model.Session;
import com.example.nomad.nomad.model.Ticket;
import com.example.nomad.nomad.repository.SessionRepository;
import com.example.nomad.nomad.service.session.SessionService;
import com.example.nomad.nomad.service.ticket.TicketService;
import com.example.nomad.nomad.service.ticket.impl.TicketServiceImpl;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.logging.Logger;

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
            List<TicketDto> activeTickets = ticketService.getTicketsDtoByStatuses(TicketStatus.NEW, TicketStatus.INSERVICE);

            for (TicketDto ticket : activeTickets) {
                Duration duration = Duration.between(ticket.getRegistrationTime(), now);

                if (duration.toHours() >= 8) {
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


