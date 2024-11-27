package com.digiex.utility.web.controller;

import com.digiex.utility.exception.ApplicationErrorCode;
import com.digiex.utility.utility.web.model.res.ApiResp;
import com.digiex.utility.web.model.dto.UserDTO;
import com.digiex.utility.web.service.imp.RoleService;
import com.digiex.utility.web.service.imp.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.util.Set;
import java.util.UUID;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/user")
public class UserController {
  UserService userService;
  RoleService roleService;

  private final ResourceBundleMessageSource messageSource;

  public UserController(
      UserService userService, RoleService roleService, ResourceBundleMessageSource messageSource) {
    this.userService = userService;
    this.roleService = roleService;
    this.messageSource = messageSource;
  }

  @PostMapping
  public ResponseEntity<?> CreateUser(@Valid @RequestBody UserDTO userDTO) {
    UserDTO savedUser = userService.save(userDTO);

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(savedUser.getId())
            .toUri();
    return ResponseEntity.created(location)
        .body(ApiResp.builder().success(true).data(savedUser).build());
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getUserById(
      @NotNull(message = "user.id.not_null") @PathVariable String id) {
    try {
      UUID userId = UUID.fromString(id);
      UserDTO user = userService.getUserById(userId);
      return ResponseEntity.ok().body(ApiResp.builder().success(true).data(user).build());
    } catch (IllegalArgumentException ex) {

      var messageError =
          messageSource.getMessage("error.invalid.uuid", null, LocaleContextHolder.getLocale());
      var error =
          ApiResp.ErrorResp.builder()
              .code(ApplicationErrorCode.NOT_FOUND_HTTP_REQUEST_RESOURCE)
              .message(messageError)
              .build();

      var response = ApiResp.builder().error(error).build();

      return ResponseEntity.badRequest().body(response);
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateUser(
      @PathVariable String id, @Valid @RequestBody UserDTO updatedUserDTO) {

    if (updatedUserDTO == null) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT)
          .body(ApiResp.ErrorResp.builder().message("Body empty").build());
    }

    try {
      UUID userId = UUID.fromString(id);
      UserDTO updatedUser = userService.updateUser(userId, updatedUserDTO);
      return ResponseEntity.ok().body(ApiResp.builder().success(true).data(updatedUser).build());
    } catch (IllegalArgumentException ex) {
      var messageError =
          messageSource.getMessage("error.invalid.uuid", null, LocaleContextHolder.getLocale());
      var error =
          ApiResp.ErrorResp.builder()
              .code(ApplicationErrorCode.NOT_FOUND_HTTP_REQUEST_RESOURCE)
              .message(messageError)
              .build();

      var response = ApiResp.builder().error(error).build();

      return ResponseEntity.badRequest().body(response);
    }
  }

  @PutMapping("/{id}/roles")
  public ResponseEntity<?> updateUserRole(
      @PathVariable UUID userId, @RequestBody Set<Long> roleIds) {
    try {
      // UUID userId = UUID.fromString(id);
      UserDTO updatedUser = userService.updateUserRole(userId, roleIds);
      return ResponseEntity.ok().body(ApiResp.builder().success(true).data(updatedUser).build());
    } catch (IllegalArgumentException ex) {
      var messageError =
          messageSource.getMessage("error.invalid.uuid", null, LocaleContextHolder.getLocale());
      var error =
          ApiResp.ErrorResp.builder()
              .code(ApplicationErrorCode.NOT_FOUND_HTTP_REQUEST_RESOURCE)
              .message(messageError)
              .build();

      var response = ApiResp.builder().error(error).build();

      return ResponseEntity.badRequest().body(response);
    }
  }
}
