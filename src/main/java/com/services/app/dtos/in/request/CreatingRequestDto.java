package com.services.app.dtos.in.request;

import com.services.app.entities.EmployeeEntity;
import com.services.app.entities.ServiceEntity;
import lombok.Data;

@Data
public class CreatingRequestDto {
    private String header;
    private String body;
    private Long idAuthor;
    private Long idService;
    private EmployeeEntity employee;
    private ServiceEntity service;
}
