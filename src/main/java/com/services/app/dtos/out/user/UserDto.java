package com.services.app.dtos.out.user;

import com.services.app.dictionaries.Status;
import com.services.app.entities.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
  private Long id;
  private String username;
  private String employeeName;
  private Status status;

  public UserDto(UserEntity entity) {
    this.username = entity.getUsername();
    this.employeeName = entity.getEmployee().getFullName();
    this.id = entity.getId();
    this.status = entity.getStatus();
  }
}
