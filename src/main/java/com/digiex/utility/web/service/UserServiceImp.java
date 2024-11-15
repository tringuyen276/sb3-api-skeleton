package com.digiex.utility.web.service;

import com.digiex.utility.util.PasswordUtil;
import com.digiex.utility.web.model.Role;
import com.digiex.utility.web.model.User;
import com.digiex.utility.web.model.UserRole;
import com.digiex.utility.web.model.dto.RoleDTO;
import com.digiex.utility.web.model.dto.UserDTO;
import com.digiex.utility.web.repository.RoleReposity;
import com.digiex.utility.web.repository.UserRepository;
import com.digiex.utility.web.service.imp.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

  @Autowired private UserRepository userRepository;

  @Autowired private RoleReposity roleReposity;

  @Override
  public UserDTO updateUser(UUID id, UserDTO userDTO) {

    User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("user"));
    user.setFirstName(userDTO.getFirstName());
    user.setLastName(userDTO.getLastName());
    user.setEmail(userDTO.getEmail());
    user.setUsername(userDTO.getUsername());
    if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
      user.setPassword(PasswordUtil.encode(userDTO.getPassword()));
    }
    userRepository.save(user);
    return UserDTO.builder().id(user.getId()).build();
  }

  @Override
  public UserDTO getUserById(UUID id) {
    User user =
        userRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));

    Set<RoleDTO> roles =
        user.getRoles().stream()
            .map(
                userRole -> {
                  Role role = userRole.getRole();
                  return RoleDTO.builder().id(role.getId()).name(role.getName()).build();
                })
            .collect(Collectors.toSet());

    return UserDTO.builder()
        .id(user.getId())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .email(user.getEmail())
        .username(user.getUsername())
        .roles(roles)
        .build();
  }

  @Override
  @Transactional
  public UserDTO save(UserDTO userDTO) {
    User user =
        User.builder()
            .firstName(userDTO.getFirstName())
            .lastName(userDTO.getLastName())
            .email(userDTO.getEmail())
            .username(userDTO.getUsername())
            .password(PasswordUtil.encode(userDTO.getPassword()))
            .build();
    if (userRepository.existsByUsername(user.getUsername())) {
      throw new DataIntegrityViolationException("username");
    }
    userRepository.save(user);

    if (userDTO.getRoles() != null && !userDTO.getRoles().isEmpty()) {
      Set<UserRole> userRoles =
          userDTO.getRoles().stream()
              .map(
                  roleDTO -> {
                    Role role =
                        roleReposity
                            .findById(roleDTO.getId())
                            .orElseThrow(() -> new EntityNotFoundException("Role"));
                    return UserRole.builder().user(user).role(role).build();
                  })
              .collect(Collectors.toSet());
      userRoles.forEach(item -> System.out.println(item.getRole().getName()));
      user.setRoles(userRoles);

      Set<RoleDTO> roles =
          user.getRoles().stream()
              .map(
                  userRole -> {
                    Role role = userRole.getRole();
                    return RoleDTO.builder().id(role.getId()).name(role.getName()).build();
                  })
              .collect(Collectors.toSet());
      userDTO.setRoles(roles);
    }

    userDTO.setId(user.getId());
    return userDTO;
  }

  public User findUserByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public boolean validUser(String username, String password) {
    User user = findUserByUsername(username);
    if (user == null) {
      return false;
    }
    return PasswordUtil.comapareHash(password, user.getPassword());
  }
}
