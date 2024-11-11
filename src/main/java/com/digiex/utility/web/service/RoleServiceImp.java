package com.digiex.utility.web.service;

import com.digiex.utility.web.model.Permission;
import com.digiex.utility.web.model.Role;
import com.digiex.utility.web.model.dto.PermissionDTO;
import com.digiex.utility.web.model.dto.RoleDTO;
import com.digiex.utility.web.repository.RoleReposity;
import com.digiex.utility.web.service.imp.RoleService;

import java.sql.Timestamp;
import java.util.*;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImp implements RoleService {

  @Autowired private RoleReposity roleReposity;
  @Autowired private ModelMapper modelMapper;

  @Override
  public RoleDTO save(RoleDTO roleDTO) {
    Role role = modelMapper.map(roleDTO, Role.class);
    Role savedRole = roleReposity.save(role);
    return modelMapper.map(savedRole, RoleDTO.class);
  }

  @Override
  public RoleDTO updateRole(long roleId, RoleDTO updateRole) {
    Role role=modelMapper.map(updateRole,Role.class);
    Role existingRole = roleReposity.findById(roleId).get();

    existingRole.setName(role.getName());
    existingRole.setDescription(role.getDescription());
    existingRole.setPermissions(role.getPermissions());

    Role savedRole = roleReposity.save(existingRole);
    return modelMapper.map(savedRole, RoleDTO.class);
  }

  @Override
  public RoleDTO deleteRole(long id) {
    Role role=roleReposity.findById(id).orElseThrow(() -> new EntityNotFoundException("Permission not found with id: " + id));
    role.setDeletedAt(new Timestamp(System.currentTimeMillis()));;
    roleReposity.save(role);
    return modelMapper.map(role, RoleDTO.class);
  }

  @Override
  public RoleDTO getRoleById(long id) {
    Role role = roleReposity.findById(id) .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + id));
    if(role.getDeletedAt()!=null){
      throw new EntityNotFoundException("Role with id " + id + " has already been deleted.");
    }
    return modelMapper.map(role, RoleDTO.class);
  }

  public Set<Permission> findPermissionByRoleId(Long roleId) {
    Optional<Role> role = roleReposity.findById(roleId);
    return role.map(Role::getPermissions).orElse(Collections.emptySet());
  }




}
