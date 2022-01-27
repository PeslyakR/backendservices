package com.services.app.entities;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;
import com.services.app.entities.base.TitledEntity;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@Entity
@Setter
@Getter
@Table(name = "s_departments")
@NoArgsConstructor
public class DepartmentEntity extends TitledEntity {

  private LocalDateTime servCreated;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "departament")
  @Column(name = "id_dep")
  private List<PositionEntity> positions;

}
