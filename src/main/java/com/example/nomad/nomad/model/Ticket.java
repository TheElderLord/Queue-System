package com.example.nomad.nomad.model;

import com.example.nomad.nomad.Enum.TicketStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    @JoinColumn(name = "session_id",nullable = false)
    private Session session;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operator_id")
    private Operator operator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "window_number")
    private Window window;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id",nullable = false)
    private Branch branch;




    @Column(name = "registration_time")
    private LocalDateTime registrationTime;

    @Column(name = "service_start_time")
    private LocalDateTime serviceStartTime;

    @Column(name = "service_end_time")
    private LocalDateTime serviceEndTime;


    @Column(name = "status",nullable = false)
    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @Column(name = "booking_time")
    private LocalDateTime bookingTime;

    @Column(name = "booking_code")
    private int bookingCode;


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
