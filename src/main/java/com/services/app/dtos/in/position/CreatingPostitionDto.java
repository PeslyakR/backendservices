package com.services.app.dtos.in.position;

import com.services.app.entities.DepartmentEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatingPostitionDto {
    private Long idDepartament;
    private String title;
    private String description;
    private DepartmentEntity departament;
}
