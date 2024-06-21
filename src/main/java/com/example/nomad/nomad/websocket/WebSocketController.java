package com.example.nomad.nomad.websocket;

import com.example.nomad.nomad.dto.ticket.TicketDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/start")
    @SendTo("/topic/tickets")
    public TicketDto start(TicketDto ticket) {
        return ticket; // This is just a simple example, modify as needed
    }
}

