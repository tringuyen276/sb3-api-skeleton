package com.digiex.utility.web.controller;


import com.digiex.utility.web.model.Role;
import com.digiex.utility.web.model.User;
import com.digiex.utility.web.repository.RoleReposity;
import com.digiex.utility.web.repository.UserRepository;
import com.digiex.utility.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class UserRoleController {
    @Autowired
    UserService userService;
    @Autowired
    RoleReposity roleReposity;
    @Autowired
    UserRepository userRepository;
    @PostMapping("/UserRole/{userid}")
    public ResponseEntity<Object> addRoleUser(@PathVariable UUID userId,@PathVariable Long roleId){
       Optional<User> userOptional= userRepository.findById(userId);
       Optional<Role> roleOptional = roleReposity.findById(roleId);
       if(!roleOptional.isPresent() || !userOptional.isPresent() ){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or Role not found");
       }
       User newUser=userOptional.get();
       newUser.getRoles().add(roleOptional.get());
       userRepository.save(newUser);
       return ResponseEntity.status(HttpStatus.OK).body("Role added to User successfully");
    }

    @PutMapping("/roles/{id}")
    @Transactional
    public ResponseEntity<User> updateUserRoles(@PathVariable UUID id, @RequestBody List<Long> roleIds){
        System.out.println(roleIds);
        User updatedUser = userService.updateUserRoles(id, roleIds);
        return ResponseEntity.ok(updatedUser);
    }
}
