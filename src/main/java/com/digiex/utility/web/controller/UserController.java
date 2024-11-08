package com.digiex.utility.web.controller;

import com.digiex.utility.web.model.User;
import com.digiex.utility.web.model.dto.RoleDTO;
import com.digiex.utility.web.model.dto.UserDTO;
import com.digiex.utility.web.model.req.LoginRequest;
import com.digiex.utility.web.service.JwtService;
import com.digiex.utility.web.service.UserService;
import com.digiex.utility.web.service.imp.UserServiceImp;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
public class UserController {
  @Autowired private UserService userService;
  @Autowired private JwtService jwtService;
  @Autowired
  private ModelMapper modelMapper;
    UserServiceImp userServiceImp;
    public UserController(UserServiceImp userServiceImp){
    this.userServiceImp=userServiceImp;
  }


  @PostMapping("/login")
  public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
    boolean isValidUser =
        userService.validUser(loginRequest.getUsername(), loginRequest.getPassword());
    if (isValidUser) {
      String token =
          jwtService.generateToken(userService.findUserByUsername(loginRequest.getUsername()));
      return ResponseEntity.ok().body(token);
    }
    return ResponseEntity.badRequest().body("Invalid username or password");
  }


  @PostMapping("/User")
  public ResponseEntity<Object> CreateUser(@RequestBody UserDTO userDTO){
    UserDTO savedUser=userServiceImp.save(userDTO);
    URI location= ServletUriComponentsBuilder.
            fromCurrentRequest().
            path("/{id}").
            buildAndExpand(savedUser.getId()).
            toUri();
    return ResponseEntity.created(location).body(savedUser);
  }

  @GetMapping("/User/{id}")
  public ResponseEntity<UserDTO> getUserById(@PathVariable UUID id) {
    Optional<UserDTO> user = userServiceImp.getUserById(id);
    return user.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
  }


  @PutMapping("/User/{id}")
  public ResponseEntity<UserDTO> updateUser(@PathVariable UUID id, @RequestBody UserDTO updatedUserDTO) {
    Optional<UserDTO> existingUser = userServiceImp.getUserById(id);
    if(existingUser.isPresent()){
      UserDTO updatedUser= userServiceImp.updateUser(id,updatedUserDTO);
      return ResponseEntity.ok(updatedUser);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/User/{id}")
  public ResponseEntity<String> deleteUser(@PathVariable UUID id){
    try {
//    userRepository.deleteById(id);
      return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>("Error deleting user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
