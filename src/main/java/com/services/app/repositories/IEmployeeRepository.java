package com.services.app.repositories;

import java.util.List;

import com.services.app.dictionaries.Status;
import com.services.app.entities.EmployeeEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
  public List<EmployeeEntity> findByStatusNot(Status status);
}
