package com.digiex.utility.web.controller;

import com.digiex.utility.util.PasswordUtil;
import com.digiex.utility.web.model.User;
import com.digiex.utility.web.model.req.LoginRequest;
import com.digiex.utility.web.repository.UserRepository;
import com.digiex.utility.web.service.JwtService;
import com.digiex.utility.web.service.UserService;
import jakarta.validation.Valid;
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
  @Autowired private UserRepository userRepository;

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
  public ResponseEntity<Object> CreateUser(@RequestBody User user){

    User savedUser=userService.saveUser(user);
    URI location= ServletUriComponentsBuilder.
            fromCurrentRequest().
            path("/{id}").
            buildAndExpand(savedUser.getId()).
            toUri();
    return ResponseEntity.created(location).build();
  }

  @GetMapping("/User/{id}")
  public ResponseEntity<User> getUserById(@PathVariable UUID id) {

    Optional<User> user = userRepository.findById(id);
    return user.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PutMapping("/User/{id}")
  public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody User updatedUser) {
    Optional<User> existingUser = userRepository.findById(id);

    if (existingUser.isPresent())
    {
      User user = existingUser.get();
      user.setFirstName(updatedUser.getFirstName());
      user.setLastName(updatedUser.getLastName());
      user.setEmail(updatedUser.getEmail());
      user.setPassword(updatedUser.getPassword());
      user.setPassword(PasswordUtil.encode(user.getPassword()));
      userRepository.save(user);
      return ResponseEntity.ok(user);
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
