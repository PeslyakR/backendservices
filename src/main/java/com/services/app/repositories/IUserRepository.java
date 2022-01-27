package com.services.app.repositories;

import com.services.app.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<UserEntity, Long> {
  UserEntity findByUsername(String username);
}
