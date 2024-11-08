package com.digiex.utility.web.service.imp;

import com.digiex.utility.web.model.Permission;
import com.digiex.utility.web.model.dto.PermissionDTO;
import com.digiex.utility.web.model.dto.RoleDTO;

import java.util.Optional;

public interface PermissionServiceImp {
    PermissionDTO save(PermissionDTO permissionDTO);

    PermissionDTO updatePermission(Integer id, PermissionDTO permissionDTO);

    void deleteRole(long id);

    Optional<PermissionDTO> getPermissionById(Integer id);
}
