package com.example.nomad.nomad.dto.branch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BranchDto {
    private Long id;
    private String name;
    private String description;
    private Long parentId;
    private String parentName;
    private Double lat;
    private Double lng;

}
