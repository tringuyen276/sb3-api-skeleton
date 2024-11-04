package com.digiex.utility.web.service;

import com.digiex.utility.util.PasswordUtil;
import com.digiex.utility.web.model.User;
import com.digiex.utility.web.repository.UserRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired private UserRepository userRepository;

  public User saveUser(User user) {
    user.setPassword(PasswordUtil.encode(user.getPassword()));
    return userRepository.save(user);
  }

  public Optional<User> findUserById(UUID id) {
    return userRepository.findById(id);
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
