package com.services.app.repositories;

import java.util.List;

import com.services.app.dictionaries.Status;
import com.services.app.entities.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IServiceRepository extends JpaRepository<ServiceEntity, Long> {
  public List<ServiceEntity> findByStatusNot(Status status);
}
