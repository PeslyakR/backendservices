package com.services.app.dtos.out.user;

import java.util.Set;
import com.services.app.entities.RoleEntity;
import com.services.app.entities.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthUserDto {
  private String username;
  private String password;
  public String employeeName;
  public Set<RoleEntity> roles;
  public String depName;
  public Long empId;

  public AuthUserDto(UserEntity entity) {
    this.username = entity.getUsername();
    this.password = entity.getPassword();
    this.employeeName = entity.getEmployee().getFullName();
    this.roles = entity.getEmployee().getRoles();
    this.depName = entity.getEmployee().getPosition().getDepartament().getTitle();
    this.empId = entity.getEmployee().getId();
  }
}
