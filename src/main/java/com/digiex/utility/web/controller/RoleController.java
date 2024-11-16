package com.digiex.utility.web.controller;

import com.digiex.utility.utility.web.model.res.ApiResp;
import com.digiex.utility.web.model.dto.RoleDTO;
import com.digiex.utility.web.service.imp.RoleService;

import java.net.URI;
import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
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

    RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<?> CreateRole(@RequestBody @Valid RoleDTO roleDTO) {
        RoleDTO savedRole = roleService.save(roleDTO);
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
        RoleDTO roleDTO = roleService.getRoleById(id);
        return ResponseEntity.ok().body(ApiResp.builder().success(true).data(roleDTO).build());
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateRole(@PathVariable Long id, @RequestBody @Valid RoleDTO updatedRoleDTO) {
        RoleDTO updatedRole = roleService.updateRole(id, updatedRoleDTO);
        return ResponseEntity.ok().body(ApiResp.builder().success(true).data(updatedRole).build());
    }


    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok().body(ApiResp.builder().success(true).build());
    }
}
