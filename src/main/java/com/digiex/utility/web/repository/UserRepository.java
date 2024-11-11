package com.digiex.utility.web.repository;

import java.util.UUID;

import com.digiex.utility.web.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
  User findByUsername(String username);
  boolean existsByUsername(String username);
}
