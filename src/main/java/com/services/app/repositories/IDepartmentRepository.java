package com.services.app.repositories;

import java.util.List;
import com.services.app.dictionaries.Status;
import com.services.app.entities.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDepartmentRepository extends JpaRepository<DepartmentEntity, Long> {

  public List<DepartmentEntity> findByStatusNot(Status status);
}
