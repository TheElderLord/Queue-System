package com.example.nomad.nomad.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@NoArgsConstructor
public class WindowDto {
    private Long id;
    private Long branchId;
    private String branchName;
    private Long number;
    private String name;
    private String description;
    private boolean active;
    private List<WindowServiceModelDto> windowServiceModelDtos;

}
