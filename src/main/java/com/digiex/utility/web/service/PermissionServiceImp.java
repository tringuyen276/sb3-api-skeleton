package com.digiex.utility.web.service;

import com.digiex.utility.web.model.Permission;
import com.digiex.utility.web.model.dto.PermissionDTO;
import com.digiex.utility.web.repository.PermissionRepository;
import com.digiex.utility.web.service.imp.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImp implements PermissionService {
  @Autowired private PermissionRepository permissionRepository;

  @Override
  public PermissionDTO save(PermissionDTO permissionDTO) {
    Permission p = new Permission();
    p.setName(permissionDTO.getName());
    Permission savedPermission = permissionRepository.save(p);

    permissionDTO.setId(savedPermission.getId());
    permissionDTO.setCreateAt(savedPermission.getCreatedAt());
    return permissionDTO;
  }

  @Override
  public PermissionDTO updatePermission(Integer id, PermissionDTO updatePermission) {
    /*
     * Permission existingPermission =
     * permissionRepository
     * .findById(id)
     * .orElseThrow(() -> new IllegalArgumentException("Role not found with id: " +
     * id));
     * existingPermission.setName(updatePermission.getName());
     * Permission savedPermission = permissionRepository.save(existingPermission);
     * return modelMapper.map(savedPermission, PermissionDTO.class);
     */
    return PermissionDTO.builder().id(null).build();
  }

  @Override
  public PermissionDTO deleteRole(Integer id) {
    /*
     * Permission permission =
     * permissionRepository
     * .findById(id)
     * .orElseThrow(() -> new
     * EntityNotFoundException("Permission not found with id: " + id));
     * ;
     * permission.setDeletedAt(new Timestamp(System.currentTimeMillis()));
     * permissionRepository.save(permission);
     * return modelMapper.map(permission, PermissionDTO.class);
     */
    return PermissionDTO.builder().id(null).build();
  }

  @Override
  public PermissionDTO getPermissionById(Integer id) {
    /*
     * Permission permission =
     * permissionRepository
     * .findById(id)
     * .orElseThrow(() -> new
     * EntityNotFoundException("Permission not found with id: " + id));
     * ;
     * if (permission.getDeletedAt() != null) {
     * throw new EntityNotFoundException("Permission with id " + id +
     * " has already been deleted.");
     * }
     * return modelMapper.map(permission, PermissionDTO.class);
     */
    return PermissionDTO.builder().id(null).build();
  }
}
