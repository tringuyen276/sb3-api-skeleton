package com.digiex.utility.web.controller;

import com.digiex.utility.utility.web.model.res.ApiResp;
import com.digiex.utility.web.model.dto.PermissionDTO;
import com.digiex.utility.web.service.imp.PermissionService;
import jakarta.persistence.EntityNotFoundException;
import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/permission")
public class PermissionController {

  // @Autowired private ModelMapper modelMapper;

  PermissionService permissionService;

  public PermissionController(PermissionService permissionService) {
    this.permissionService = permissionService;
  }

  @PostMapping
  public ResponseEntity<Object> CreateRepository(@RequestBody PermissionDTO permissionDTO) {
    PermissionDTO savedPermission = permissionService.save(permissionDTO);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(savedPermission.getId())
            .toUri();
    return ResponseEntity.created(location)
        .body(ApiResp.builder().success(true).data(savedPermission).build());
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getPermissionById(@PathVariable int id) {
    try {

      PermissionDTO permission = permissionService.getPermissionById(id);

      return ResponseEntity.ok().body(ApiResp.builder().success(true).data(permission).build());
    } catch (EntityNotFoundException ex) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(ApiResp.ErrorResp.builder().message("Permission not found").build());
    } catch (Exception ex) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(ApiResp.ErrorResp.builder().message("Internal server error").build());
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateRole(
      @PathVariable int id, @RequestBody PermissionDTO updatedPermissionDTO) {
    PermissionDTO existingPermission = permissionService.getPermissionById(id);
    if (existingPermission != null) {
      PermissionDTO updatedPermission =
          permissionService.updatePermission(id, updatedPermissionDTO);
      return ResponseEntity.ok()
          .body(ApiResp.builder().success(true).data(updatedPermission).build());
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(ApiResp.ErrorResp.builder().message("Permission not found").build());
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteRole(@PathVariable int id) {
    try {
      permissionService.deleteRole(id);
      return new ResponseEntity<>("Role deleted successfully", HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(
          "Error deleting Role: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
