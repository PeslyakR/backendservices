package com.services.app.dtos.out.departament;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.services.app.dictionaries.Status;
import com.services.app.dtos.out.position.PositionDto;
import com.services.app.entities.DepartmentEntity;
import com.services.app.entities.EmployeeEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;

@Data
@NoArgsConstructor
public class ExtendedDepartmentDto {
  private Long id;
  private String title;
  private String description;
  private List<PositionDto> positions = new ArrayList<PositionDto>();;
  private Status status;
  private LocalDateTime updated;

  Set<Empl> employees = new HashSet<Empl>();

  public ExtendedDepartmentDto(DepartmentEntity dep) {
    this.id = dep.getId();
    this.title = dep.getTitle();
    this.description = dep.getDescription();

    this.status = dep.getStatus();
    this.updated = dep.getUpdated();

    if (dep.getPositions() != null) {

      dep.getPositions().stream()
          .forEach(p -> {
            if (p.getStatus() == Status.ACTIVE) {
              this.positions.add(new PositionDto(p));
            }

            p.getEmployees().stream()
                .forEach(
                    e -> {
                      if (e.getStatus() == Status.ACTIVE) {
                        this.employees.add(new Empl(e));
                      }

                    });
          }

          );
    }
  }

  @Data
  class Empl {
    private Long id;
    private String fullName;
    private String address;
    private String position;

    public Empl(EmployeeEntity e) {
      this.id = e.getId();
      this.fullName = e.getFullName();
      this.address = e.getAddress();
      this.position = e.getPosition().getTitle();
    }
  }
}
