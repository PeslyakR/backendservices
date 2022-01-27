package com.services.app.dtos.in.departament;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatingDepartmentDto {
  private String title;
  private String description;
}
