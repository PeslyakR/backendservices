package com.services.app.entities;

import javax.persistence.*;
import com.services.app.entities.base.BaseEntity;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@Entity
@Setter
@Getter
@Table(name = "s_users")
@NoArgsConstructor
public class UserEntity extends BaseEntity {

  private String username;
  private String password;
  @OneToOne
  @JoinColumn(name = "id_emp", nullable = false)
  private EmployeeEntity employee;

}
