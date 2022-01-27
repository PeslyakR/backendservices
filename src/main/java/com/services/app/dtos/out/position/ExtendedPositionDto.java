package com.services.app.dtos.out.position;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.services.app.dictionaries.Status;
import com.services.app.entities.PositionEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExtendedPositionDto {

  private Long id;
  private String departamentName;
  private String title;
  private String description;
  private List<String> employeesNames;
  private Status status;
  private LocalDateTime updated;

  public ExtendedPositionDto(PositionEntity pos) {
    this.id = pos.getId();
    this.departamentName = pos.getDepartament().getTitle();
    this.title = pos.getTitle();
    this.description = pos.getDescription();
    this.status = pos.getStatus();
    if (pos.getEmployees() != null)
      this.employeesNames = pos.getEmployees()
          .stream()
          .map(p -> {
            return p.getFullName();
          })
          .collect(Collectors.toList());
  }
}
