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
    @Column(name = "window_num",nullable = false)
    private Long number;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id",nullable = false)
    private Branch branch;

//    @Column(name = "status")
//    private String status;

    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "active",nullable = false,columnDefinition = "boolean default false")
    private boolean active;
    private String description;


}
