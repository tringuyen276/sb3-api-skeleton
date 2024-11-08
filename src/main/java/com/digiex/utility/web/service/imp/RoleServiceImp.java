package com.digiex.utility.web.service.imp;

import com.digiex.utility.web.model.dto.RoleDTO;
import java.util.Optional;

public interface RoleServiceImp {
  RoleDTO save(RoleDTO roleDTO);

  RoleDTO updateRole(long id, RoleDTO roleDTO);

  void deleteRole(long id);

  Optional<RoleDTO> getRoleById(long id);
}
