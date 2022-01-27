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
@Table(name = "s_services")
public class ServiceEntity extends TitledEntity {

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "service")
  @Column(name = "id_role")
  private List<RoleEntity> roles;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "service")
  @Column(name = "id_req", nullable = false)
  private List<RequestEntity> requests;

}
