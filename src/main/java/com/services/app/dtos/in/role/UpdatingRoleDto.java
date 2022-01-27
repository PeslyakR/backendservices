package com.services.app.dtos.in.role;

import lombok.Data;

@Data
public class UpdatingRoleDto {
    private Long id;
    private String title;
    private String description;
    private String name;
}
