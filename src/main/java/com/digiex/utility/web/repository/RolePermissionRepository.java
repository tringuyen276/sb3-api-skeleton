package com.digiex.utility.web.repository;

import com.digiex.utility.web.model.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePermissionRepository extends JpaRepository<RolePermission,Long> {
}
