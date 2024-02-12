package com.example.notes.repository;

import com.example.notes.domain.entity.UsersEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UsersEntity, String> {

  UsersEntity findByUsername(String username);

  @Transactional
  @Modifying
  @Query(value = "insert into users(username, password) values (?, ?)", nativeQuery = true)
  void insertNewUser(String username, String password);
}
