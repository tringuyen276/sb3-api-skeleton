package com.digiex.utility.web.service;

import com.digiex.utility.util.PasswordUtil;
import com.digiex.utility.web.model.User;
import com.digiex.utility.web.model.dto.UserDTO;
import com.digiex.utility.web.repository.RoleReposity;
import com.digiex.utility.web.repository.UserRepository;
import com.digiex.utility.web.service.imp.UserServiceImp;
import java.util.Optional;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserServiceImp {

  @Autowired private RoleReposity roleReposity;
  @Autowired private ModelMapper modelMapper;
  @Autowired private UserRepository userRepository;

  @Override
  public UserDTO save(UserDTO userDTO) {
    User user = modelMapper.map(userDTO, User.class);
    user.setPassword(PasswordUtil.encode(user.getPassword()));
    User savedUser = userRepository.save(user);
    return modelMapper.map(savedUser, UserDTO.class);
  }

  @Override
  public Optional<UserDTO> getUserById(UUID id) {
    Optional<User> user = userRepository.findById(id);
    return user.map(r -> modelMapper.map(r, UserDTO.class));
  }

  @Override
  public UserDTO updateUser(UUID userId, UserDTO updateUser) {
    User existingUser =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("Role not found with id: " + userId));
    existingUser.setFirstName(updateUser.getFirstName());
    existingUser.setLastName(updateUser.getLastName());
    existingUser.setEmail(updateUser.getEmail());
    existingUser.setUsername(updateUser.getUsername());
    User savedUser = userRepository.save(existingUser);
    return modelMapper.map(savedUser, UserDTO.class);
  }

  public User findUserByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  public void deleteUser(UUID id) {}

  public boolean validUser(String username, String password) {
    User user = findUserByUsername(username);
    if (user == null) {
      return false;
    }
    return PasswordUtil.comapareHash(password, user.getPassword());
  }
}
