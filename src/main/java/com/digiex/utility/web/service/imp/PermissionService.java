package com.digiex.utility.web.service.imp;

import com.digiex.utility.web.model.dto.PermissionDTO;
import java.util.Optional;

public interface PermissionService {
  PermissionDTO save(PermissionDTO permissionDTO);

  PermissionDTO updatePermission(Integer id, PermissionDTO permissionDTO);

  PermissionDTO deleteRole(Integer id);

  PermissionDTO getPermissionById(Integer id);
}
