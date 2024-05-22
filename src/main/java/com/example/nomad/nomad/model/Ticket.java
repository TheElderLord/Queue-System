package com.example.nomad.nomad.model;

import com.example.nomad.nomad.Enum.TicketStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "ticket_number",nullable = false)
    private int ticketNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id",nullable = false)
    private ServiceModel serviceModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "window_id",nullable = false)
    private Session session;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id",nullable = false)
    private Branch branch;


    @Column(name = "registration_time")
    private LocalDate registrationTime;

    @Column(name = "service_start_time")
    private LocalDate serviceStartTime;

    @Column(name = "service_end_time")
    private LocalDate serviceEndTime;


    @Column(name = "status",nullable = false)
    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @Column(name = "booking_time")
    private LocalDate bookingTime;

    @Column(name = "booking_code")
    private Long bookingCode;


    @Column(name = "is_redirected",nullable = false, columnDefinition = "boolean default false")
    private boolean isDirected;

    @ManyToOne
    @JoinColumn(name = "redirected_window_id")
    private Window redirectedWindow;

    @Column(name = "agent")
    private String agent;

    @Column(name = "rating")
    private int rating;






}
