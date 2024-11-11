package com.digiex.utility.web.controller;

import com.digiex.utility.utility.web.model.res.ApiResp;
import com.digiex.utility.web.model.dto.RoleDTO;
import com.digiex.utility.web.service.imp.RoleService;

import java.net.URI;
import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Transactional
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private ModelMapper modelMapper;
    RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<?> CreateRole(@RequestBody RoleDTO roleDTO) {
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
        try {
            RoleDTO role = roleService.getRoleById(id);
            return ResponseEntity.ok().body(ApiResp.builder().success(true).data(role).build());
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResp.ErrorResp.builder().message("Role not found").build());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResp.ErrorResp.builder().message("Internal server error").build());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateRole(
            @PathVariable Long id, @RequestBody RoleDTO updatedRoleDTO) {

        RoleDTO existingRole = roleService.getRoleById(id);
        if (existingRole!=null) {
            RoleDTO updatedRole = roleService.updateRole(id, updatedRoleDTO);
            return ResponseEntity.ok().body(ApiResp.builder().success(true).data(updatedRole).build());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResp.ErrorResp.builder().message("Role not found").build());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteRole(@PathVariable Long id) {
        try {
                        roleService.deleteRole(id);
            return new ResponseEntity<>("Role deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    "Error deleting Role: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
