package com.services.app.dtos.out.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import com.services.app.dictionaries.Status;
import com.services.app.dtos.out.role.RoleDto;
import com.services.app.entities.ServiceEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExtendedServiceDto {
  private Long id;
  private String title;
  private String description;
  private Status status;
  private List<RoleDto> roles;
  private LocalDateTime updated;

  public ExtendedServiceDto(ServiceEntity dto) {
    this.id = dto.getId();
    this.title = dto.getTitle();
    this.description = dto.getDescription();
    this.status = dto.getStatus();
    this.updated = dto.getUpdated();

    if (dto.getRoles() != null) {
      this.roles = dto.getRoles().stream()
          .filter(r -> r.getStatus() != Status.DELETED)
          .map(r -> {
            return new RoleDto(r);
          }).collect(Collectors.toList());
    }
  }

}
