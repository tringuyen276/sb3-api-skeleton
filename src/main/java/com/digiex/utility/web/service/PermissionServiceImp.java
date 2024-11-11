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
  public PermissionDTO deleteRole(Integer id) {
      Permission permission=permissionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Permission not found with id: " + id));;
      permission.setDeletedAt(new Timestamp(System.currentTimeMillis()));
      permissionRepository.save(permission);
    return modelMapper.map(permission, PermissionDTO.class);
  }

  @Override
  public PermissionDTO getPermissionById(Integer id) {
    Permission permission = permissionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Permission not found with id: " + id));;
    if(permission.getDeletedAt()!=null){
      throw new EntityNotFoundException("Permission with id " + id + " has already been deleted.");
    }
    return modelMapper.map(permission,PermissionDTO.class);
  }
}
