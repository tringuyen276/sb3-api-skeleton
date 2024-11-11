package com.digiex.utility.web.controller;

import com.digiex.utility.utility.web.model.res.ApiResp;
import com.digiex.utility.web.model.Role;
import com.digiex.utility.web.model.User;
import com.digiex.utility.web.model.dto.RoleDTO;
import com.digiex.utility.web.model.dto.UserDTO;
import com.digiex.utility.web.service.imp.RoleService;
import com.digiex.utility.web.service.imp.UserService;
import jakarta.persistence.EntityNotFoundException;
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
    UserService userService;
    RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<?> CreateUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO savedUser = userService.save(userDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).body(ApiResp.builder().success(true).data(savedUser).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        try {
            UUID userId = UUID.fromString(id);
            UserDTO user = userService.getUserById(userId);
            return ResponseEntity.ok().body(ApiResp.builder().success(true).data(user).build());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResp.ErrorResp.builder().message("Invalid UUID format").build());
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResp.ErrorResp.builder().message("User not found").build());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResp.ErrorResp.builder().message("Internal server error").build());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody UserDTO updatedUserDTO) {
        if (updatedUserDTO == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(ApiResp.ErrorResp.builder().message("Body empty").build());
        }
        try {
            UUID userId = UUID.fromString(id);
            UserDTO existingUser = userService.getUserById(userId);
            if (existingUser != null) {
                UserDTO updatedUser = userService.updateUser(userId, updatedUserDTO);
                return ResponseEntity.ok().body(ApiResp.builder().success(true).data(updatedUser).build());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResp.ErrorResp.builder().message("User not found").build());
            }
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResp.ErrorResp.builder().message("Invalid UUID format").build());
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResp.ErrorResp.builder().message("User not found").build());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResp.ErrorResp.builder().message("An error occurred while updating user").build());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id) {
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
