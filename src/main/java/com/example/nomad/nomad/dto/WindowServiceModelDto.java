package com.example.nomad.nomad.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class WindowServiceModelDto {
    private Long id;
    //    private Long branchId;
//    private String branchName;
    private Long windowId;
    //    private String roleName;
    private Long serviceId;
    private String serviceName;
    private String parentServiceName;

}
