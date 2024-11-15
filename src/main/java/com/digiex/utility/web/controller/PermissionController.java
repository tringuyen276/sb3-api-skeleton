package com.digiex.utility.web.controller;

import com.digiex.utility.utility.web.model.res.ApiResp;
import com.digiex.utility.web.model.dto.PermissionDTO;
import com.digiex.utility.web.service.imp.PermissionService;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/permission")
public class PermissionController {

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

    PermissionDTO permission = permissionService.getPermissionById(id);

    return ResponseEntity.ok().body(ApiResp.builder().success(true).data(permission).build());
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateRole(
      @PathVariable int id, @RequestBody PermissionDTO updatedPermissionDTO) {
    PermissionDTO updatedPermission = permissionService.updatePermission(id, updatedPermissionDTO);
    return ResponseEntity.ok()
        .body(ApiResp.builder().success(true).data(updatedPermission).build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteRole(@PathVariable int id) {
    permissionService.deletePermission(id);
    return ResponseEntity.ok().body("Permission deleted successfully");
  }
}
