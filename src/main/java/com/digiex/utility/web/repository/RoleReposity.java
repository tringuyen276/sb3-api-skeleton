package com.digiex.utility.web.repository;

import com.digiex.utility.web.model.Role;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RoleReposity extends JpaRepository<Role, Long> {
  Role findRoleByName(String name);

  Optional<Role> findRoleById(Long id);
}
