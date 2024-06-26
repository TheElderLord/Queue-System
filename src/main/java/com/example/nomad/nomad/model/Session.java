package com.example.nomad.nomad.model;

import com.example.nomad.nomad.Enum.SessionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "sessions")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "status",nullable = false )
    @Enumerated(EnumType.STRING)
    private SessionStatus status=SessionStatus.ONNLINE;
    @Column(name = "start_time",nullable = false)
    private ZonedDateTime startTime;
    @Column(name = "end_time")
    private ZonedDateTime endTime;
    @Column(name = "active",nullable = false, columnDefinition = "boolean default false")
    private boolean active;
    @ManyToOne
    @JoinColumn(name = "operator_id")
    private Operator operator;
    @ManyToOne
    @JoinColumn(name = "window_id",nullable = false)
    private Window window;
    @ManyToOne
    @JoinColumn(name = "branch_id",nullable = false)
    private Branch branch;

}
