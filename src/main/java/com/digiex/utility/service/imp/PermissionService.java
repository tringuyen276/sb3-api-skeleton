package com.digiex.utility.service.imp;

import com.digiex.utility.web.model.dto.PermissionDTO;

public interface PermissionService {
  PermissionDTO save(PermissionDTO permissionDTO);

  PermissionDTO updatePermission(Long id, PermissionDTO permissionDTO);

  PermissionDTO deleteRole(Long id);

  PermissionDTO getPermissionById(Long id);
}
