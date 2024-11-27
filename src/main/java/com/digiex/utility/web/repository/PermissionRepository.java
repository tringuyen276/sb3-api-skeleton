package com.digiex.utility.web.repository;

import com.digiex.utility.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {
  Permission findPermissionByName(String name);

  Permission findPermissionById(Integer id);
}
