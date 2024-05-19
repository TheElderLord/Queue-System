package com.example.nomad.nomad.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class OperatorDto {
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private String lastname;
    private Long roleId;
    private boolean active;
}

