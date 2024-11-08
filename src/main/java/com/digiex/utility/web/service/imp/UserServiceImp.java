package com.digiex.utility.web.service.imp;

import com.digiex.utility.web.model.dto.UserDTO;
import java.util.Optional;
import java.util.UUID;

public interface UserServiceImp {
  UserDTO save(UserDTO userDTO);

  UserDTO updateUser(UUID id, UserDTO roleDTO);

  void deleteUser(UUID id);

  Optional<UserDTO> getUserById(UUID id);
}
