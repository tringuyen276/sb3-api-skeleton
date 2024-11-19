package com.digiex.utility.service;

import com.digiex.utility.entity.Permission;
import com.digiex.utility.repository.PermissionRepository;
import com.digiex.utility.service.imp.PermissionService;
import com.digiex.utility.web.model.dto.PermissionDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PermissionServiceImp implements PermissionService {
  @Autowired private PermissionRepository permissionRepository;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public PermissionDTO save(PermissionDTO permissionDTO) {
    Permission permission = new Permission();
    permission.setName(permissionDTO.getName());

    return PermissionDTO.convertToDto(permissionRepository.save(permission));
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public PermissionDTO updatePermission(Long id, PermissionDTO permissionDTO) {
    Permission existingPermission =
        permissionRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Permission not found"));
    existingPermission.setName(permissionDTO.getName());
    return PermissionDTO.convertToDto(permissionRepository.save(existingPermission));
  }

  @Override
  public PermissionDTO deleteRole(Long id) {
    return null;
  }

  @Override
  public PermissionDTO getPermissionById(Long id) {
    Permission permission =
        permissionRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Permission not found"));
    return PermissionDTO.convertToDto(permission);
  }
}
