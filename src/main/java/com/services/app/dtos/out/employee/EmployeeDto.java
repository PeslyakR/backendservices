package com.services.app.dtos.out.employee;

import java.time.LocalDate;
import com.services.app.dictionaries.Status;
import com.services.app.dtos.out.position.PositionDto;
import com.services.app.dtos.out.user.UserDto;
import com.services.app.entities.EmployeeEntity;
import lombok.Data;

@Data
public class EmployeeDto {

  private Long id;
  private String fullName;
  private String address;
  private LocalDate beginWorking;
  private LocalDate endWorking;
  private PositionDto position;
  private Status status;
  private UserDto user;

  public EmployeeDto(EmployeeEntity entity) {
    this.id = entity.getId();
    this.fullName = entity.getFullName();
    this.address = entity.getAddress();
    this.position = new PositionDto(entity.getPosition());
    this.beginWorking = entity.getBeginWorking();
    this.setStatus(entity.getStatus());

    this.endWorking = entity.getEndWorking();
    if (entity.getUser() != null)
      this.setUser(new UserDto(entity.getUser()));
  }
}
