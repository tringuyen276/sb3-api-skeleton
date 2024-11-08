package com.digiex.utility.web.controller;

import com.digiex.utility.web.model.dto.PermissionDTO;
import com.digiex.utility.web.service.imp.PermissionServiceImp;
import java.net.URI;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class PermissionController {

  @Autowired private ModelMapper modelMapper;

  PermissionServiceImp permissionServiceImp;

  public PermissionController(PermissionServiceImp permissionServiceImp) {
    this.permissionServiceImp = permissionServiceImp;
  }

  @PostMapping("/Permission")
  public ResponseEntity<Object> CreateRepository(@RequestBody PermissionDTO permissionDTO) {
    PermissionDTO savedPermission = permissionServiceImp.save(permissionDTO);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(savedPermission.getId())
            .toUri();
    return ResponseEntity.created(location).body(savedPermission);
  }

  @GetMapping("/Permission/{id}")
  public ResponseEntity<PermissionDTO> getPermissionById(@PathVariable int id) {
    Optional<PermissionDTO> permission = permissionServiceImp.getPermissionById(id);
    return permission.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PutMapping("/Permission/{id}")
  public ResponseEntity<PermissionDTO> updateRole(
      @PathVariable int id, @RequestBody PermissionDTO updatedPermissionDTO) {
    Optional<PermissionDTO> existingPermission = permissionServiceImp.getPermissionById(id);
    if (existingPermission.isPresent()) {
      PermissionDTO updatedPermission =
          permissionServiceImp.updatePermission(id, updatedPermissionDTO);
      return ResponseEntity.ok(updatedPermission);
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
      return new ResponseEntity<>(
          "Error deleting Role: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
