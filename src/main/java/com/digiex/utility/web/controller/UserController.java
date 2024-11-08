package com.digiex.utility.web.controller;

import com.digiex.utility.utility.web.model.res.ApiResp;
import com.digiex.utility.web.model.dto.UserDTO;
import com.digiex.utility.web.service.imp.UserServiceImp;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/user")
public class UserController {
  UserServiceImp userServiceImp;

  public UserController(UserServiceImp userServiceImp) {
    this.userServiceImp = userServiceImp;
  }

  @PostMapping
  public ResponseEntity<Object> CreateUser(@Valid @RequestBody UserDTO userDTO) {
    UserDTO savedUser = userServiceImp.save(userDTO);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(savedUser.getId())
            .toUri();
    return ResponseEntity.created(location)
        .body(ApiResp.builder().success(true).data(savedUser).build());
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getUserById(@PathVariable UUID id) {
    Optional<UserDTO> user = userServiceImp.getUserById(id);
    if (user.isPresent()) {
      return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(ApiResp.ErrorResp.builder().message("User not found").build());
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserDTO> updateUser(
      @PathVariable UUID id, @RequestBody UserDTO updatedUserDTO) {
    Optional<UserDTO> existingUser = userServiceImp.getUserById(id);
    if (existingUser.isPresent()) {
      UserDTO updatedUser = userServiceImp.updateUser(id, updatedUserDTO);
      return ResponseEntity.ok(updatedUser);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteUser(@PathVariable UUID id) {
    try {
      // userRepository.deleteById(id);
      return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(
          "Error deleting user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
