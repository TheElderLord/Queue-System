package com.example.nomad.nomad.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "windows")
public class Window {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branch;


    @NonNull
    @Column(name = "status")
    private String status;

    @NonNull
    @Column(name = "name")
    private String name;

    @NonNull
    @Column(name = "active",columnDefinition = "boolean default false")
    private boolean active;

    private String description;


}
