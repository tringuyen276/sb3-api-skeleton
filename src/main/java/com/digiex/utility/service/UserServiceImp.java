package com.digiex.utility.service;

import com.digiex.utility.entity.Role;
import com.digiex.utility.entity.User;
import com.digiex.utility.entity.UserRole;
import com.digiex.utility.repository.RoleReposity;
import com.digiex.utility.repository.UserRepository;
import com.digiex.utility.repository.UserRoleRepository;
import com.digiex.utility.service.imp.UserService;
import com.digiex.utility.web.model.dto.UserDTO;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImp implements UserService {
  @Autowired private UserRepository userRepository;
  @Autowired RoleReposity roleReposity;
  @Autowired UserRoleRepository userRoleRepository;

  @Override
  @Transactional
  public UserDTO save(UserDTO userDTO) {
    User user = userDTO.convertToEntity();
    if (userRepository.existsByUsername(userDTO.getUsername())) {
      throw new DuplicateKeyException("Duplicate Name: " + userDTO.getUsername());
    }
    userRepository.save(user);
    if (userDTO.getRoles() != null) {
      List<UserRole> userRoles =
          userDTO.getRoles().stream()
              .map(
                  roleDTO -> {
                    Role role =
                        roleReposity
                            .findById(roleDTO.getId())
                            .orElseThrow(() -> new EntityNotFoundException("Role not found"));
                    UserRole userRole = new UserRole();
                    userRole.setUser(user);
                    userRole.setRole(role);
                    return userRole;
                  })
              .collect(Collectors.toList());
      userRoleRepository.saveAll(userRoles);
    }
    return UserDTO.convertToDto(user);
  }

  @Override
  public UserDTO updateUser(UUID id, UserDTO userDTO) {
    User existingUser =
        userRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));
    if (userRepository.existsByUsername(userDTO.getUsername())) {
      throw new DuplicateKeyException("Duplicate Name: " + userDTO.getUsername());
    }
    existingUser.setUsername(userDTO.getUsername());
    existingUser.getUserRoles().clear();

    if (userDTO.getRoles() != null) {
      userDTO
          .getRoles()
          .forEach(
              roleDto -> {
                Role role =
                    roleReposity
                        .findById(roleDto.getId())
                        .orElseThrow(() -> new EntityNotFoundException("Role not found"));
                UserRole userRole = new UserRole();
                userRole.setUser(existingUser);
                userRole.setRole(role);
                existingUser.getUserRoles().add(userRole);
              });
    }
    return UserDTO.convertToDto(userRepository.save(existingUser));
  }

  @Override
  public UserDTO deleteUser(UUID id) {
    return null;
  }

  @Override
  public UserDTO getUserById(UUID id) {
    User user =
        userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("id:" + id));

    /* List<RoleDTO> roleDtos =
    user.getUserRoles().stream()
        .map(userRole -> RoleDTO.convertToDto(userRole.getRole()))
        .collect(Collectors.toList()); */
    return UserDTO.builder()
        .id(user.getId())
        .username(user.getUsername())
        // .roles(roleDtos)
        .build();
  }
}
