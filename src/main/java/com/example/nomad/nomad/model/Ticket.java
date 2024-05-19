package com.example.nomad.nomad.model;

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
    private String id;

    @Column(name = "ticket_number")
    private int ticketNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private ServiceModel serviceModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "window_id")
    private Window window;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branch;


    @Column(name = "registration_time")
    private LocalDate registrationTime;

    @Column(name = "service_start_time")
    private LocalDate serviceStartTime;

    @Column(name = "service_end_time")
    private LocalDate serviceEndTime;

    @NonNull
    @Column(name = "status")
    private String status;

    @Column(name = "booking_time")
    private LocalDate bookingTime;

    @Column(name = "booking_code")
    private int bookingCode;

    @NonNull
    @Column(name = "is_redirected")
    private boolean isDirected;

    @ManyToOne
    @JoinColumn(name = "redirected_window_id")
    private Window redirectedWindow;

    @Column(name = "agent")
    private String agent;

    @Column(name = "rating")
    private int rating;






}
