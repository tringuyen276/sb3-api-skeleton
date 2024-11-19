package com.digiex.utility.service.imp;

import com.digiex.utility.web.model.dto.UserDTO;
import java.util.UUID;

public interface UserService {
  UserDTO save(UserDTO userDTO);

  UserDTO updateUser(UUID id, UserDTO userDTO);

  UserDTO deleteUser(UUID id);

  UserDTO getUserById(UUID id);
}
