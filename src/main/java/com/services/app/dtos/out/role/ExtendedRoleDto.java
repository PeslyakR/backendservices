package com.services.app.dtos.out.role;

import com.services.app.dictionaries.Status;
import com.services.app.dtos.out.service.ServiceDto;
import com.services.app.entities.RoleEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExtendedRoleDto {
  private Long id;
  private ServiceDto service;
  private String title;
  private String description;
  private String name;
  private Status status;

  public ExtendedRoleDto(RoleEntity role) {
    this.id = role.getId();
    this.title = role.getTitle();
    this.description = role.getDescription();
    this.service = new ServiceDto(role.getService());
    this.name = role.getName();
    this.status = role.getStatus();
  }
}
