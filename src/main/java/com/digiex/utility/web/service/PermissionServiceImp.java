package com.digiex.utility.web.service;

import com.digiex.utility.web.model.Permission;
import com.digiex.utility.web.model.dto.PermissionDTO;
import com.digiex.utility.web.repository.PermissionRepository;
import java.sql.Timestamp;
import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.digiex.utility.web.service.imp.PermissionService;

@Service
public class PermissionServiceImp implements PermissionService {
  @Autowired private PermissionRepository permissionRepository;


  @Override
  public PermissionDTO save(PermissionDTO permissionDTO) {
    Permission permission = permissionDTO.convertToEntity();
    return PermissionDTO.convertToDto(permissionRepository.save(permission));
  }



  @Override
  public PermissionDTO updatePermission(Long id, PermissionDTO permissionDTO) {
    Permission existingPermission = permissionRepository.findById(id)
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
    Permission permission = permissionRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Permission not found"));
    return PermissionDTO.convertToDto(permission);
  }
}
