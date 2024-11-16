package com.digiex.utility.web.controller;

import com.digiex.utility.utility.web.model.res.ApiResp;
import com.digiex.utility.web.model.dto.UserDTO;
import com.digiex.utility.web.service.imp.UserService;

import java.net.URI;
import java.util.UUID;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/user")
public class UserController {
    UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> CreateUser( @RequestBody @Valid  UserDTO userDTO) {
        UserDTO savedUser = userService.save(userDTO);
        URI location =
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(savedUser.getId())
                        .toUri();
        return ResponseEntity.created(location)
                .body(ApiResp.builder().success(true).data(savedUser).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById( @PathVariable String id) {
        try{
            UUID userId = UUID.fromString(id);
            UserDTO userDto = userService.getUserById(userId);
            return ResponseEntity.ok().body(ApiResp.builder().success(true).data(userDto).build());
        }catch (IllegalArgumentException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResp.ErrorResp.builder().message("Invalid UUID format").build());
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id,@Valid @RequestBody UserDTO updatedUserDTO) {
        try{
            UUID userId = UUID.fromString(id);
        UserDTO updatedUser = userService.updateUser(userId,updatedUserDTO);
        return ResponseEntity.ok().body(ApiResp.builder().success(true).data(updatedUser).build());}
        catch (IllegalArgumentException ex){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResp.ErrorResp.builder().message("Invalid UUID format").build());
            }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        UUID newUUID=UUID.fromString(id);
        userService.deleteUser(newUUID);
        return ResponseEntity.ok().body(ApiResp.builder().success(true).build());
}

}
