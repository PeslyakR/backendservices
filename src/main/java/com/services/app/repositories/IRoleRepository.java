package com.services.app.repositories;

import java.util.Set;

import com.services.app.dictionaries.Status;
import com.services.app.entities.RoleEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<RoleEntity, Long> {
  RoleEntity findByName(String name);

  RoleEntity findByTitle(String title);

  Set<RoleEntity> findByStatusNot(Status status);

}
