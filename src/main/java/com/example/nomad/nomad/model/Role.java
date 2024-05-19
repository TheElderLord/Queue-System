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
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NonNull
    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @NonNull
    @Column(name = "priority")
    private String priority;

    public Role(Long roleId) {
        this.id = roleId;
    }

//    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
//    private List<Operator> operators;
//
//    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
//    private List<RoleService> services;
}
