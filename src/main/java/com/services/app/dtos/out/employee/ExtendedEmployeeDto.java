package com.services.app.dtos.out.employee;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import com.services.app.dictionaries.Status;
import com.services.app.dtos.out.role.RoleDto;
import com.services.app.entities.EmployeeEntity;
import com.services.app.entities.PositionEntity;

import lombok.Data;

@Data
public class ExtendedEmployeeDto {

  private Long id;
  private String fullName;
  private String address;

  private LocalDate beginWorking;
  private LocalDate endWorking;
  private Status status;
  private Set<RoleDto> roles;
  private Number positionId;
  private PositionEntity position;
  private LocalDateTime updated;
  private String username;
  private Number idUser;

  public ExtendedEmployeeDto(EmployeeEntity entity) {
    this.id = entity.getId();
    this.fullName = entity.getFullName();
    this.address = entity.getAddress();
    this.beginWorking = entity.getBeginWorking();
    this.endWorking = entity.getEndWorking();
    this.status = entity.getStatus();
    this.positionId = entity.getPosition().getId();
    this.updated = entity.getUpdated();
    if (entity.getUser() != null) {
      this.username = entity.getUser().getUsername();
      this.idUser = entity.getUser().getId();
    }
    if (entity.getRoles() != null)
      this.roles = entity.getRoles().stream()
          .map(r -> new RoleDto(r))
          .collect(Collectors.toSet());
  }
}
