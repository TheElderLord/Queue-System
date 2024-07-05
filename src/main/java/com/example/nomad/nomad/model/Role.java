package com.example.nomad.nomad.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "priority",nullable = false)
    private Long priority;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoleServiceModel> roleServiceModels;

    public Role(Long roleId) {
        this.id = roleId;
    }

//    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
//    private List<Operator> operators;
//
//    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
//    private List<RoleService> services;
}
