package com.example.nomad.nomad.background;


import com.example.nomad.nomad.Enum.SessionStatus;
import com.example.nomad.nomad.model.Session;
import com.example.nomad.nomad.repository.SessionRepository;
import com.example.nomad.nomad.service.session.SessionService;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class SessionChecker {

    private final SessionRepository sessionRepository;
    private final SessionService sessionService;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SessionChecker.class);

    // Scheduled to run every minute
    @Scheduled(fixedDelay = 60000)
    public void checkSessions() {
        try {
            ZoneId gmtPlus5 = ZoneId.of("GMT+5");
            ZonedDateTime now = ZonedDateTime.now(gmtPlus5);

            // Fetch all active sessions
            List<Session> activeSessions = sessionRepository.findAllByActive(true);

            for (Session session : activeSessions) {
                Duration duration = Duration.between(session.getStartTime(), now);

                if (duration.toHours() >= 8) {
                    // Stop the session
                    sessionService.stopASession(session.getId(), SessionStatus.FORCED);
                    // Log the action if needed
                    logger.info("Session with ID " + session.getId() + " has been stopped after 8 hours.");
                }
            }
        } catch (Exception e) {
            // Handle exceptions to prevent the scheduler from halting
            e.printStackTrace();
        }
    }
}

