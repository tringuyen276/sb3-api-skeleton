package com.digiex.utility.web.controller;

import com.digiex.utility.web.model.req.LoginRequest;
import com.digiex.utility.web.service.JwtService;
import com.digiex.utility.web.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
  @Autowired private UserService userService;
  @Autowired private JwtService jwtService;

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
}
