package com.digiex.utility.web.repository;

import com.digiex.utility.web.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {
  Permission findPermissionByName(String name);

  Permission findPermissionById(Integer id);
}
