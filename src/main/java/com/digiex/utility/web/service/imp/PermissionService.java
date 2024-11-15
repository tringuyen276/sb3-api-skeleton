package com.digiex.utility.web.service.imp;

import com.digiex.utility.web.model.dto.PermissionDTO;

public interface PermissionService {
  PermissionDTO save(PermissionDTO permissionDTO);

  PermissionDTO updatePermission(Integer id, PermissionDTO permissionDTO);

  void deletePermission(Integer id);

  PermissionDTO getPermissionById(Integer id);
}
