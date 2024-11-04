package com.digiex.utility.web.controller;

import com.digiex.utility.utility.web.model.res.ApiResp;
import com.digiex.utility.web.model.Permission;
import com.digiex.utility.web.repository.PermissionRepository;
import com.digiex.utility.web.service.RoleService;
import com.digiex.utility.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
public class UserController {
  @Autowired private UserService userService;
  @Autowired private RoleService roleService;
  @Autowired private PermissionRepository permissionRepository;

  private void test(){
    UUID id=UUID.fromString("e294c2b3-b7bf-478f-8363-e70c6130583e");
    Set<Permission> a= roleService.findPermissionByRoleId(id);
    List<Permission> b=permissionRepository.findAll();
   // System.out.println(b);
    System.out.println(a);
  }
  @GetMapping(path="/Users")
  public ResponseEntity<?> get() {
    test();
    return ResponseEntity.ok().body(ApiResp.builder().success(true).build());
  }
}
