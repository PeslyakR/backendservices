package com.services.app.entities.base;

import java.time.LocalDateTime;

import javax.persistence.*;
import com.services.app.dictionaries.Status;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.NoArgsConstructor;

import lombok.Setter;
import lombok.Getter;

@Setter
@Getter
@MappedSuperclass
@NoArgsConstructor
public abstract class BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @CreatedDate
  @Column(name = "ent_created")
  private LocalDateTime created;

  @LastModifiedDate
  @Column(name = "ent_updated")
  private LocalDateTime updated;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", columnDefinition = "varchar(20) default 'ACTIVE'")
  private Status status;

  public BaseEntity(Long id) {
    this.id = id;
  }
}
