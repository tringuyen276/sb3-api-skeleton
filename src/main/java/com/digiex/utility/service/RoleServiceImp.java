package com.digiex.utility.service;

import com.digiex.utility.entity.Role;
import com.digiex.utility.entity.RolePermission;
import com.digiex.utility.repository.PermissionRepository;
import com.digiex.utility.repository.RolePermissionRepository;
import com.digiex.utility.repository.RoleReposity;
import com.digiex.utility.service.imp.RoleService;
import com.digiex.utility.web.model.dto.RoleDTO;
import com.digiex.utility.web.model.req.CreateRoleReq;
import com.digiex.utility.web.model.res.RoleRes;
import jakarta.persistence.EntityNotFoundException;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RoleServiceImp implements RoleService {
  @Autowired RoleReposity roleReposity;
  @Autowired PermissionRepository permissionRepository;
  @Autowired RolePermissionRepository rolePermissionRepository;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public RoleRes save(CreateRoleReq req) {
    if (roleReposity.existsByName(req.getName())) {
      throw new DuplicateKeyException("Duplicate Name: " + req.getName());
    }

    var permissions =
        req.getPermissions().stream()
            .map(
                id -> {
                  return permissionRepository
                      .findById(id)
                      .orElseThrow(
                          () ->
                              new EntityNotFoundException(
                                  String.format("Permission id '%s' not found", id)));
                })
            .collect(Collectors.toList());

    var role = new Role();
    role.setName(req.getName());

    var rolePermissions =
        permissions.stream()
            .map(
                permission -> {
                  var rolePermission = new RolePermission();

                  rolePermission.setPermission(permission);
                  rolePermission.setRole(role);

                  return rolePermission;
                })
            .collect(Collectors.toList());

    role.setRolePermissions(rolePermissions);

    roleReposity.save(role);

    return RoleRes.fromEntity(role);
  }

  @Override
  public RoleDTO updateRole(long id, RoleDTO roleDTO) {
    Role existingRole =
        roleReposity.findById(id).orElseThrow(() -> new EntityNotFoundException("Role not found"));
    existingRole.setName(roleDTO.getName());
    existingRole.getRolePermissions().clear();
    /* if (roleDTO.getPermissions() != null) {
      roleDTO
          .getPermissions()
          .forEach(
              permissionDto -> {
                Permission permission =
                    permissionRepository
                        .findById(permissionDto.getId())
                        .orElseThrow(() -> new EntityNotFoundException("Permission not found"));
                RolePermission rolePermission = new RolePermission();
                rolePermission.setPermission(permission);
                rolePermission.setRole(existingRole);
                existingRole.getRolePermissions().add(rolePermission);
              });
    } */
    return null; // RoleDTO.convertToDto(roleReposity.save(existingRole));
  }

  @Override
  public RoleDTO deleteRole(long id) {
    //    roleReposity.deleteById(id);
    return null;
  }

  @Override
  public RoleRes getRoleById(long id) {
    Role role =
        roleReposity
            .findById(id)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(String.format("Permission id '%s' not found", id)));

    return RoleRes.fromEntity(role);
  }
}
