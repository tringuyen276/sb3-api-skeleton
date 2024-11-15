package com.digiex.utility.web.service;

import com.digiex.utility.web.model.Permission;
import com.digiex.utility.web.model.Role;
import com.digiex.utility.web.model.RolePermission;
import com.digiex.utility.web.model.dto.RoleDTO;
import com.digiex.utility.web.repository.PermissionRepository;
import com.digiex.utility.web.repository.RolePermissionRepository;
import com.digiex.utility.web.repository.RoleReposity;
import com.digiex.utility.web.service.imp.RoleService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImp implements RoleService {

  @Autowired private RoleReposity roleReposity;

  @Autowired private PermissionRepository permissionRepository;

  @Autowired private RolePermissionRepository rolePermissionRepository;

  @Override
  @Transactional
  public RoleDTO save(RoleDTO roleDTO) {
    Role role = new Role();
    role.setName(roleDTO.getName());

    Role savedRole = roleReposity.save(role);

    return savedRole.convertToDTO();
  }

  @Override
  public RoleDTO updateRole(long roleId, RoleDTO updateRole) {

    Role role =
        roleReposity.findById(roleId).orElseThrow(() -> new EntityNotFoundException("role"));
    role.setName(updateRole.getName());
    roleReposity.save(role);
    return role.convertToDTO();
  }

  @Override
  public RoleDTO deleteRole(long id) {

    Role role =
        roleReposity.findById(id).orElseThrow(() -> new EntityNotFoundException("permission"));

    roleReposity.delete(role);

    return RoleDTO.builder().id(null).build();
  }

  @Override
  public RoleDTO getRoleById(long id) {
    Role role = roleReposity.findById(id).orElseThrow(() -> new EntityNotFoundException());

    return role.convertToDTO();
  }

  @Override
  @Transactional
  public RoleDTO updateRolePermissions(Long id, Set<Integer> permissionIds) {
    Role role = roleReposity.findById(id).orElseThrow(() -> new EntityNotFoundException("role"));
    Set<RolePermission> list = rolePermissionRepository.findAllPermissionRoleByRoleId(id);
    rolePermissionRepository.deleteAllInBatch(list);

    Set<RolePermission> rolePermission =
        permissionIds.stream()
            .map(
                permissionId -> {
                  Permission permission =
                      permissionRepository
                          .findById(permissionId)
                          .orElseThrow(() -> new EntityNotFoundException("permission"));
                  return RolePermission.builder().role(role).permission(permission).build();
                })
            .collect(Collectors.toSet());

    role.setPermissions(rolePermission);

    roleReposity.save(role);
    return role.convertToDTO();
  }
}
