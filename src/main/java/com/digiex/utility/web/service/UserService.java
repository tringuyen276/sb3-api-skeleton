package com.digiex.utility.web.service;

import com.digiex.utility.web.model.User;
import com.digiex.utility.web.repository.UserRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;

public class UserService {

  @Autowired private UserRepository userRepository;

  public User saveUser(User user) {
    return userRepository.save(user);
  }

  public Optional<User> findUserById(UUID id) {
    return userRepository.findById(id);
  }

  public User findUserByUsername(String username) {
    return userRepository.findByUsername(username);
  }
}
