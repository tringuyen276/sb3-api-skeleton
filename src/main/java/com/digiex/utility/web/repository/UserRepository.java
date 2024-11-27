package com.digiex.utility.web.repository;

import com.digiex.utility.entity.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
  User findByUsername(String username);

  boolean existsByUsername(String username);
}
