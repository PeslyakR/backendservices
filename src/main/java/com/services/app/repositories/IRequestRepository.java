package com.services.app.repositories;

import java.util.List;

import com.services.app.dictionaries.Status;
import com.services.app.entities.RequestEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IRequestRepository extends JpaRepository<RequestEntity, Long> {
  public List<RequestEntity> findByStatusNot(Status status);
}
