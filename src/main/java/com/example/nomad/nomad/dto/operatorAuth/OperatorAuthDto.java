package com.example.nomad.nomad.dto.operatorAuth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperatorAuthDto {
    private String username;
    private String password;
}
