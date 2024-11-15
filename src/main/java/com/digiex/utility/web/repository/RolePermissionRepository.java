package com.digiex.utility.web.repository;

import com.digiex.utility.web.model.RolePermission;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {

  RolePermission findPermissionRoleByRoleId(Long roleId);

  RolePermission findPermissionRoleByPermissionId(Long permissionId);

  RolePermission findPermissionRoleByRoleIdAndPermissionId(Long roleId, Long permissionId);

  Set<RolePermission> findAllPermissionRoleByRoleIdAndPermissionId(Long roleId, Long permissionId);

  Set<RolePermission> findAllPermissionRoleByRoleId(Long roleId);
}
