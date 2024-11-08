package com.digiex.utility.web.service;

import com.digiex.utility.web.model.Permission;
import com.digiex.utility.web.model.dto.PermissionDTO;
import com.digiex.utility.web.repository.PermissionRepository;
import com.digiex.utility.web.service.imp.PermissionServiceImp;
import java.sql.Timestamp;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionService implements PermissionServiceImp {
  @Autowired private PermissionRepository permissionRepository;
  @Autowired private ModelMapper modelMapper;

  @Override
  public PermissionDTO save(PermissionDTO permissionDTO) {
    Permission permission = modelMapper.map(permissionDTO, Permission.class);
    permission.setCreatedAt(new Timestamp(System.currentTimeMillis()));
    Permission savedPermission = permissionRepository.save(permission);
    return modelMapper.map(savedPermission, PermissionDTO.class);
  }

  @Override
  public PermissionDTO updatePermission(Integer id, PermissionDTO updatePermission) {
    Permission existingPermission =
        permissionRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Role not found with id: " + id));
    existingPermission.setName(updatePermission.getName());
    existingPermission.setDescription(updatePermission.getDescription());
    Permission savedPermission = permissionRepository.save(existingPermission);
    return modelMapper.map(savedPermission, PermissionDTO.class);
  }

  @Override
  public void deleteRole(long id) {}

  @Override
  public Optional<PermissionDTO> getPermissionById(Integer id) {
    Optional<Permission> permission = permissionRepository.findById(id);
    return permission.map(r -> modelMapper.map(r, PermissionDTO.class));
  }
}
