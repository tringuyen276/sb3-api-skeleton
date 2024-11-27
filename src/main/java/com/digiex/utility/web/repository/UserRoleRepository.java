package com.digiex.utility.web.repository;

import com.digiex.utility.entity.UserRole;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
  UserRole findUserRoleByUserId(UUID userId);

  UserRole findUserRoleByRoleId(Long roleId);

  UserRole findUserRoleByUserIdAndRoleId(UUID userId, Long roleId);
}
