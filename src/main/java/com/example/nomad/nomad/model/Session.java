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
@Table(name = "sessions")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NonNull
    @Column(name = "status")
    private String status;
    @NonNull
    @Column(name = "start_time")
    private LocalDate startTime;
    @NonNull
    @Column(name = "end_time")
    private LocalDate endTime;

    @ManyToOne
    @JoinColumn(name = "operator_id")
    private Operator operator;

    @ManyToOne
    @JoinColumn(name = "window_id")
    private Window window;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

}
