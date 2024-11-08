package com.digiex.utility.web.controller;

import com.digiex.utility.web.model.Role;
import com.digiex.utility.web.model.dto.RoleDTO;
import com.digiex.utility.web.service.RoleService;
import com.digiex.utility.web.service.imp.RoleServiceImp;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Transactional
@RestController
public class RoleController {
    @Autowired
    private ModelMapper modelMapper;
    RoleServiceImp roleServiceImp;
    public RoleController(RoleServiceImp roleServiceImp){
        this.roleServiceImp=roleServiceImp;
    }

    @PostMapping("/Role")
    public ResponseEntity<RoleDTO> CreateRole(@RequestBody RoleDTO roleDTO)
    {
        RoleDTO postResponse=roleServiceImp.save(roleDTO);
        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest().
                path("/{id}").
                buildAndExpand(postResponse.getId()).
                toUri();
        return ResponseEntity.created(location).body(postResponse);
    }

    @GetMapping("/Role/{id}")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable Long id) {
        Optional<RoleDTO> role = roleServiceImp.getRoleById(id);
        return role.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/Role/{id}")
    public ResponseEntity<RoleDTO> updateRole(@PathVariable Long id, @RequestBody RoleDTO updatedRoleDTO) {
        Optional<RoleDTO> existingRole = roleServiceImp.getRoleById(id);
        if (existingRole.isPresent()) {
            RoleDTO updatedRole = roleServiceImp.updateRole(id, updatedRoleDTO);
            return ResponseEntity.ok(updatedRole);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/Role/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable Long id) {
        try {
//            roleReposity.deleteById(id);
            return new ResponseEntity<>("Role deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting Role: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
