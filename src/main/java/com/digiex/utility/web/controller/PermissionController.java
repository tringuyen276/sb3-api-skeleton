package com.digiex.utility.web.controller;


import com.digiex.utility.web.model.Permission;
import com.digiex.utility.web.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
public class PermissionController {
    @Autowired
    PermissionRepository permissionRepository;

    @PostMapping("/Permission")
    public ResponseEntity<Object> CreateRepository(@RequestBody Permission permission)
    {
        Permission savedPermission = permissionRepository.save(permission);
        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest().
                path("/{id}").
                buildAndExpand(savedPermission.getId()).
                toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/Permission/{id}")
    public ResponseEntity<Permission> getPermissionById(@PathVariable int id) {
        Optional<Permission> permission = permissionRepository.findById(id);
        return permission.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/Permission/{id}")
    public ResponseEntity<Permission> updateRole(@PathVariable int id, @RequestBody Permission updatedPermission) {
        Optional<Permission> existingRole = permissionRepository.findById(id);
        if (existingRole.isPresent()) {
            Permission permission = existingRole.get();
            permission.setName(updatedPermission.getName());
            permission.setDescription(updatedPermission.getDescription());
            return ResponseEntity.ok(permission);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/Permission/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable int id) {
        try {
//            permissionRepository.deleteById(id);
            return new ResponseEntity<>("Role deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting Role: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
