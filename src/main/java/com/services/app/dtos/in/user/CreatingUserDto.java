package com.services.app.dtos.in.user;

import com.services.app.entities.EmployeeEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatingUserDto {
    private String username;
    private String password;
    private Long idEmployee;
    private EmployeeEntity employee;
}
