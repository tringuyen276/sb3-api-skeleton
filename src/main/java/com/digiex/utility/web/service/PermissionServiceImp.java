package com.digiex.utility.web.service;

import com.digiex.utility.entity.Permission;
import com.digiex.utility.web.model.dto.PermissionDTO;
import com.digiex.utility.web.repository.PermissionRepository;
import com.digiex.utility.web.service.imp.PermissionService;
import jakarta.persistence.EntityNotFoundException;
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
    Permission p =
        permissionRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("permission"));
    p.setName(updatePermission.getName());
    permissionRepository.save(p);
    return p.convertToDTO();
  }

  @Override
  public void deletePermission(Integer id) {
    Permission p =
        permissionRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("permission"));
    permissionRepository.delete(p);
  }

  @Override
  public PermissionDTO getPermissionById(Integer id) {
    Permission p =
        permissionRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("permission"));
    return p.convertToDTO();
  }
}
