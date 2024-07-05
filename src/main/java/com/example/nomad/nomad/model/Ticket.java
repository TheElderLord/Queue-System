package com.example.nomad.nomad.model;

import com.example.nomad.nomad.Enum.Language;
import com.example.nomad.nomad.Enum.TicketStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

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
    @JoinColumn(name = "service_id",nullable = true)
    private ServiceModel serviceModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id")
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
    private ZonedDateTime registrationTime;

    @Column(name = "service_start_time")
    private ZonedDateTime serviceStartTime;

    @Column(name = "service_end_time")
    private ZonedDateTime serviceEndTime;


    @Column(name = "status",nullable = false)
    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @Column(name = "booking_time")
    private ZonedDateTime bookingTime;

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
    @Column(name = "comment")
    private String comment;
    @Column(name = "lang")
    private Language language;






}
