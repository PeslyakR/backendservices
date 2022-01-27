package com.services.app.entities.base;

import javax.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@Setter
@Getter
@MappedSuperclass
@NoArgsConstructor
public abstract class TitledEntity extends BaseEntity {

  @Column(name = "title", columnDefinition = "varchar(50)", unique = true)
  private String title;

  @Column(name = "description", columnDefinition = "varchar(255)")
  private String description;

  protected TitledEntity(String title, String description) {
    this.title = title;
    this.description = description;
  }

  protected TitledEntity(Long id, String title, String description) {
    super(id);
    this.title = title;
    this.description = description;
  }

}
