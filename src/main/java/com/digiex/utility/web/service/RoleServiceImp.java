package com.digiex.utility.web.service;
import com.digiex.utility.web.model.Permission;
import com.digiex.utility.web.model.Role;
import com.digiex.utility.web.model.RolePermission;
import com.digiex.utility.web.model.UserRole;
import com.digiex.utility.web.model.dto.PermissionDTO;
import com.digiex.utility.web.model.dto.RoleDTO;
import com.digiex.utility.web.model.dto.UserDTO;
import com.digiex.utility.web.repository.PermissionRepository;
import com.digiex.utility.web.repository.RolePermissionRepository;
import com.digiex.utility.web.repository.RoleReposity;
import com.digiex.utility.web.service.imp.RoleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImp implements RoleService {
  @Autowired
  RoleReposity roleReposity;
  @Autowired
  PermissionRepository permissionRepository;
  @Autowired
  RolePermissionRepository rolePermissionRepository;

  @Override
  @Transactional
  public RoleDTO save(RoleDTO roleDTO) {
    Role role = roleDTO.convertToEntity();
      if(roleReposity.existsByName(roleDTO.getName())){
          throw new DuplicateKeyException("Duplicate Name: " + role.getName());
      }
    roleReposity.save(role);
    if (roleDTO.getPermissions() != null) {
      List<RolePermission> rolePermissions = roleDTO.getPermissions().stream()
              .map(permissionDTO -> {
                Permission permission = permissionRepository.findById(permissionDTO.getId())
                        .orElseThrow(() -> new EntityNotFoundException("Permission not found"));
                RolePermission rolePermission = new RolePermission();
                rolePermission.setRole(role);
                rolePermission.setPermission(permission);
                return rolePermission;
              })
              .collect(Collectors.toList());
      rolePermissionRepository.saveAll(rolePermissions);

    }
    return roleDTO.convertToDto(roleReposity.save(role));
  }

  @Override
  public RoleDTO updateRole(long id, RoleDTO roleDTO) {
   Role existingRole=roleReposity.findById(id)
           .orElseThrow(() -> new EntityNotFoundException("Role not found"));
   existingRole.setName(roleDTO.getName());
   existingRole.getRolePermissions().clear();
   if(roleDTO.getPermissions()!=null ){
     roleDTO.getPermissions().forEach(permissionDto -> {
       Permission permission = permissionRepository.findById(permissionDto.getId())
               .orElseThrow(() -> new EntityNotFoundException("Permission not found"));
       RolePermission rolePermission = new RolePermission();
       rolePermission.setPermission(permission);
       rolePermission.setRole(existingRole);
       existingRole.getRolePermissions().add(rolePermission);
     });
   }
    return RoleDTO.convertToDto(roleReposity.save(existingRole));
  }

  @Override
  public RoleDTO deleteRole(long id) {
//    roleReposity.deleteById(id);
    return null;
  }

  @Override
  public RoleDTO getRoleById(long id) {
    Role role = roleReposity.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Role not found"));

    List<PermissionDTO> permissionDtos = role.getRolePermissions().stream()
            .map(rolePermission -> PermissionDTO.convertToDto(rolePermission.getPermission()))
            .collect(Collectors.toList());

    return RoleDTO.builder()
            .id(role.getId())
            .name(role.getName())
            .permissions(permissionDtos)
            .build();
  }
}
