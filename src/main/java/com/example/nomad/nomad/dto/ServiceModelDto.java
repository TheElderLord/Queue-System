package com.example.nomad.nomad.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class ServiceModelDto {
    private Long id;
    private String name;
    private String description;
//    private Long maxServTime;
//    private Long maxWaitTime;
    private String parentName;
    private Long priority;
    private Long parentId;

}
