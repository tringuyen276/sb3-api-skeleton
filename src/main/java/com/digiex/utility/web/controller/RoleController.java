package com.digiex.utility.web.controller;

import com.digiex.utility.service.imp.RoleService;
import com.digiex.utility.utility.web.model.res.ApiResp;
import com.digiex.utility.web.model.dto.RoleDTO;
import com.digiex.utility.web.model.req.CreateRoleReq;
import com.digiex.utility.web.model.res.RoleRes;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
public class RoleController {
  private final RoleService roleService;

  @PostMapping
  public ResponseEntity<?> CreateRole(@RequestBody @Valid CreateRoleReq req) {
    RoleRes savedRole = roleService.save(req);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(savedRole.getId())
            .toUri();
    return ResponseEntity.created(location)
        .body(ApiResp.builder().success(true).data(savedRole).build());
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getRoleById(@PathVariable Long id) {
    RoleRes roleDTO = roleService.getRoleById(id);
    return ResponseEntity.ok().body(ApiResp.builder().success(true).data(roleDTO).build());
  }

  @PutMapping("{id}")
  public ResponseEntity<?> updateRole(
      @PathVariable Long id, @RequestBody @Valid RoleDTO updatedRoleDTO) {
    RoleDTO updatedRole = roleService.updateRole(id, updatedRoleDTO);
    return ResponseEntity.ok().body(ApiResp.builder().success(true).data(updatedRole).build());
  }

  @DeleteMapping("{id}")
  public ResponseEntity<?> deleteRole(@PathVariable Long id) {
    roleService.deleteRole(id);
    return ResponseEntity.ok().body(ApiResp.builder().success(true).build());
  }
}
