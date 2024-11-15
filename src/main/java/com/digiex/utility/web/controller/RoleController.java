package com.digiex.utility.web.controller;

import com.digiex.utility.utility.web.model.res.ApiResp;
import com.digiex.utility.web.model.dto.RoleDTO;
import com.digiex.utility.web.service.imp.RoleService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.Set;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Transactional
@RestController
@RequestMapping("/role")
public class RoleController {
  RoleService roleService;

  public RoleController(RoleService roleService) {
    this.roleService = roleService;
  }

  @PostMapping
  public ResponseEntity<?> CreateRole(@Valid @RequestBody RoleDTO roleDTO) {
    RoleDTO postResponse = roleService.save(roleDTO);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(postResponse.getId())
            .toUri();

    return ResponseEntity.created(location)
        .body(ApiResp.builder().success(true).data(postResponse).build());
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getRoleById(@PathVariable Long id) {
    RoleDTO role = roleService.getRoleById(id);
    return ResponseEntity.ok().body(ApiResp.builder().success(true).data(role).build());
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateRole(@PathVariable Long id, @RequestBody RoleDTO updatedRoleDTO) {

    RoleDTO updatedRole = roleService.updateRole(id, updatedRoleDTO);
    return ResponseEntity.ok().body(ApiResp.builder().success(true).data(updatedRole).build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteRole(@PathVariable Long id) {
    roleService.deleteRole(id);
    return ResponseEntity.ok().body(ApiResp.builder().success(true).build());
  }

  @PutMapping("/{id}/permissions")
  public ResponseEntity<?> updateRolePermissions(
      @PathVariable Long id, @RequestBody Set<Integer> permissionIds) {
    RoleDTO updatedRole = roleService.updateRolePermissions(id, permissionIds);
    return ResponseEntity.ok().body(ApiResp.builder().success(true).data(updatedRole).build());
  }
}
