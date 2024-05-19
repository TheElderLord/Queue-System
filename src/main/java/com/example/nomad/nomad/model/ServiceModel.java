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
@Table(name = "services")
public class ServiceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @NonNull
    @Column(name = "max_serv_time",nullable = false)
    private Long maxServTime;

    @NonNull
    @Column(name = "max_wait_time",nullable = false)
    private Long maxWaitTime;

    @NonNull
    @Column(name = "priority",nullable = false)
    private Long priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private ServiceModel parentService;


    public ServiceModel(Long roleId) {
        this.id = roleId;
    }
}
