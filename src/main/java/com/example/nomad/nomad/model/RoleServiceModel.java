package com.example.nomad.nomad.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "role_services")
public class RoleServiceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @Cascade(org.hibernate.annotations.CascadeType.MERGE)
//    @JoinColumn(name = "branch_id",nullable = false)
//    private Branch branch;

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.MERGE)
    @JoinColumn(name = "role_id",nullable = false)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.MERGE)
    @JoinColumn(name = "service_id",nullable = false)
    private ServiceModel serviceModel;

//    @OneToMany(mappedBy = "services")
//    private List<ServiceModel> serviceModels;
}
