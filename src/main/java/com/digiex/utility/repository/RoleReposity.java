package com.digiex.utility.repository;

import com.digiex.utility.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleReposity extends JpaRepository<Role, Long> {
  boolean existsByName(String name);
}
