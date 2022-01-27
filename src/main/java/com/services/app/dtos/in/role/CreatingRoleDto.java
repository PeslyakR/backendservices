package com.services.app.dtos.in.role;

import com.services.app.entities.ServiceEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatingRoleDto {

    private String title;
    private String description;
    private Long serviceId;
    private ServiceEntity service;
    private String name;
}
