package com.digiex.utility.web.service;

import com.digiex.utility.util.PasswordUtil;
import com.digiex.utility.web.model.Role;
import com.digiex.utility.web.model.User;
import com.digiex.utility.web.model.dto.RoleDTO;
import com.digiex.utility.web.model.dto.UserDTO;
import com.digiex.utility.web.repository.RoleReposity;
import com.digiex.utility.web.repository.UserRepository;
import com.digiex.utility.web.service.imp.UserService;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

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
  public UserDTO getUserById(UUID id) {
    User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    if(user.getDeletedAt()!=null){
      throw new EntityNotFoundException("User with id " + id + " has already been deleted.");
    };
    return modelMapper.map(user, UserDTO.class);
  }

  @Override
  public UserDTO updateUser(UUID userId, UserDTO updateUser) {
    User user=modelMapper.map(updateUser,User.class);
    User existingUser = userRepository.findById(userId).get();

    existingUser.setFirstName(user.getFirstName());
    existingUser.setLastName(user.getLastName());
    existingUser.setEmail(user.getEmail());
    existingUser.setUsername(user.getUsername());
    existingUser.setRoles(user.getRoles());
    User savedUser = userRepository.save(existingUser);
    return modelMapper.map(savedUser, UserDTO.class);
  }

  public User findUserByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  public UserDTO deleteUser(UUID id) {
    User user=userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Permission not found with id: " + id));
    user.setDeletedAt(new Timestamp(System.currentTimeMillis()));;
    userRepository.save(user);
    return modelMapper.map(user, UserDTO.class);
  }

  public boolean validUser(String username, String password) {
    User user = findUserByUsername(username);
    if (user == null) {
      return false;
    }
    return PasswordUtil.comapareHash(password, user.getPassword());
  }
}
