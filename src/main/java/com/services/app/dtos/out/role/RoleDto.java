package com.services.app.dtos.out.role;

import com.services.app.dictionaries.Status;
import com.services.app.entities.RoleEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoleDto {
  private Long id;
  private String serviceName;
  private String title;
  private String description;
  private String name;
  private Status status;

  public RoleDto(RoleEntity role) {
    this.id = role.getId();
    this.title = role.getTitle();
    this.description = role.getDescription();
    this.serviceName = role.getService().getTitle();
    this.name = role.getName();
    this.status = role.getStatus();
  }
}
