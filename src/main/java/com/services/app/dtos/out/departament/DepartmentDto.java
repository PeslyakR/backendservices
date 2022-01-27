package com.services.app.dtos.out.departament;

import com.services.app.dictionaries.Status;
import com.services.app.entities.DepartmentEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DepartmentDto {
    private Long id;
    private String title;
    private String description;
    private Status status;

    public DepartmentDto(DepartmentEntity dep) {
        this.id = dep.getId();
        this.title = dep.getTitle();
        this.description = dep.getDescription();
        this.status = dep.getStatus();

    }
}
