package com.digiex.utility.web.repository;

import com.digiex.utility.entity.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleReposity extends JpaRepository<Role, Long> {
  Role findRoleByName(String name);

  Optional<Role> findRoleById(Long id);
}
