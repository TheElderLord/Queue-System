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
    @Column(name = "name",nullable = false,length = 500)
    private String name;
    @Column(name = "description",length = 500)
    private String description;
    @Column(name = "max_serv_time")
    private Long maxServTime= 1000L;
    @Column(name = "max_wait_time")
    private Long maxWaitTime=1000L;
    @Column(name = "priority",nullable = false)
    private Long priority;
    @Column(name = "start_range")
    private Integer startNumberRange;
    @Column(name = "end_range")
    private Integer endNumberRange;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private ServiceModel parentService;


    public ServiceModel(Long roleId) {
        this.id = roleId;
    }
}
