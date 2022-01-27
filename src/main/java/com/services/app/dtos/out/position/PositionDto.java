package com.services.app.dtos.out.position;

import java.time.LocalDateTime;

import com.services.app.dictionaries.Status;
import com.services.app.entities.PositionEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PositionDto {
  private Long id;
  private String title;
  private String description;
  private Status status;
  private LocalDateTime updated;
  private Long idDep;

  public PositionDto(PositionEntity pos) {
    this.id = pos.getId();
    this.title = pos.getTitle();
    this.status = pos.getStatus();
    this.description = pos.getDescription();
    this.updated = pos.getUpdated();
    this.idDep = pos.getDepartament().getId();
  }
}
