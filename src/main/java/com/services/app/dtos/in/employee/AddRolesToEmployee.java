package com.services.app.dtos.in.employee;

import java.util.Set;

import com.services.app.entities.EmployeeEntity;
import com.services.app.entities.RoleEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddRolesToEmployee {
    private Long id;
    private EmployeeEntity author;
    private Set<Long> idRoles;
    private Set<RoleEntity> roles;
}
