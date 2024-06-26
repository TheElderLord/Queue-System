package com.example.nomad.nomad.dto;


import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class OperatorDto {
    private Long id;
    private String name;
    private String lastname;
    private String login;
    private String password;
    private Long roleId;
    private String roleName;
    private boolean active;
}

