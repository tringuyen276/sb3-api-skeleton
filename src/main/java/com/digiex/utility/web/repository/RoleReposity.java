package com.digiex.utility.web.repository;

import com.digiex.utility.web.model.Role;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleReposity extends JpaRepository<Role, UUID> {
  Role findRoleByName(String name);

  Role findRoleById(UUID id);
}
