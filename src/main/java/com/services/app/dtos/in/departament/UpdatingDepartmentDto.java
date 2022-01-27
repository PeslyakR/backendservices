package com.services.app.dtos.in.departament;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UpdatingDepartmentDto {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime updated;
}
