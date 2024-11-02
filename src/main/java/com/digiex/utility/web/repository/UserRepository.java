package com.digiex.utility.web.repository;

import com.digiex.utility.web.model.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {
  User findByUsername(String username);
}
