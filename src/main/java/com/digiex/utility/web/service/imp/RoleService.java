package com.digiex.utility.web.service.imp;

import com.digiex.utility.web.model.dto.RoleDTO;
import java.util.Set;

public interface RoleService {
  RoleDTO save(RoleDTO roleDTO);

  RoleDTO updateRole(long id, RoleDTO roleDTO);

  RoleDTO deleteRole(long id);

  RoleDTO getRoleById(long id);

  RoleDTO updateRolePermissions(Long id, Set<Integer> permissionIds);
}
