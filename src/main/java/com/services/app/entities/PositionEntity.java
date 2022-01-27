package com.services.app.entities;

import java.util.List;
import javax.persistence.*;
import com.services.app.entities.base.TitledEntity;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@Entity
@Setter
@Getter
@Table(name = "s_positions")
@NoArgsConstructor
public class PositionEntity extends TitledEntity {

  @ManyToOne
  @JoinColumn(name = "id_dep")
  private DepartmentEntity departament;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "position")
  @Column(name = "id_pos")
  private List<EmployeeEntity> employees;
}
