package com.example.nomad.nomad.model;

import com.example.nomad.nomad.Enum.TicketStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "ticket_status_timeout")
public class TicketStatusTimeout {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "ticket_status",nullable = false)
    private TicketStatus ticketStatus;
    @Column(name = "status_timeout")
    private int hour;
    @Column(name = "to_status")
    private TicketStatus toStatus;
}
