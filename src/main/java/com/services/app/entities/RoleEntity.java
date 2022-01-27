package com.services.app.entities;

import java.util.List;
import javax.persistence.*;
import com.services.app.entities.base.TitledEntity;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "s_roles")
public class RoleEntity extends TitledEntity {
  private String name;

  @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<EmployeeEntity> employees;

  // @OneToOne(mappedBy = "role")
  // private EmployeeEntity employee;
  @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<RequestEntity> requests;

  @ManyToOne
  @JoinColumn(name = "id_serv")
  private ServiceEntity service;

}
