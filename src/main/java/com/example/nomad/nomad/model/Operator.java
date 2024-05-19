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
@Table(name = "operators")
public class Operator {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NonNull
    @Column(name = "name",nullable = false)
    private String name;

    @NonNull
    @Column(name = "lastname",nullable = false)
    private String lastname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id",nullable = false)
    private Role role;
}
