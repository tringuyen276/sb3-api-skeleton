package com.digiex.utility.repository;

import com.digiex.utility.entity.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
  boolean existsByUsername(String name);
}
