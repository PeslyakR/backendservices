package com.services.app.repositories;

import java.util.List;

import com.services.app.dictionaries.Status;
import com.services.app.entities.PositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPositionRepository extends JpaRepository<PositionEntity, Long> {
  public List<PositionEntity> findByStatusNot(Status status);
}
