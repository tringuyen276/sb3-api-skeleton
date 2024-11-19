package com.digiex.utility.service.imp;

import com.digiex.utility.web.model.dto.RoleDTO;
import com.digiex.utility.web.model.req.CreateRoleReq;
import com.digiex.utility.web.model.res.RoleRes;

public interface RoleService {
  RoleRes save(CreateRoleReq req);

  RoleDTO updateRole(long id, RoleDTO roleDTO);

  RoleDTO deleteRole(long id);

  RoleRes getRoleById(long id);
}
