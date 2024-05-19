package com.example.nomad.nomad.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BranchDto {
    private Long id;
    private String name;
    private String description;
    private Long parentId;
}
