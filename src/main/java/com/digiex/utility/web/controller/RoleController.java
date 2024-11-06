package com.digiex.utility.web.controller;

import com.digiex.utility.web.model.Role;
import com.digiex.utility.web.repository.RoleReposity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;
@Transactional
@RestController
public class RoleController {
    @Autowired
    RoleReposity roleReposity;

    @PostMapping("/Role")
    public ResponseEntity<Object> CreateRole(@RequestBody Role role) {

        Role savedRole = roleReposity.save(role);
        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest().
                path("/{id}").
                buildAndExpand(savedRole.getId()).
                toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/Role/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
        Optional<Role> role = roleReposity.findById(id);
//        System.out.println(roleReposity.findRoleById(id).get().getUsers());
        return role.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/Role/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable Long id, @RequestBody Role updatedRole) {
        Optional<Role> existingRole = roleReposity.findById(id);

        if (existingRole.isPresent()) {
            Role role = existingRole.get();
            role.setName(updatedRole.getName());
            role.setDescription(updatedRole.getDescription());
            role.setPermissions(updatedRole.getPermissions());
            role.setUsers(updatedRole.getUsers());
            roleReposity.save(role);
            return ResponseEntity.ok(role);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/Role/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable Long id) {
        try {
            roleReposity.deleteById(id);
            return new ResponseEntity<>("Role deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting Role: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
