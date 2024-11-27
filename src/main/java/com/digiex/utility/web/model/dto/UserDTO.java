package com.digiex.utility.web.model.dto;

import com.digiex.utility.entity.User;
import com.digiex.utility.entity.UserRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDTO {
  private UUID id;

  @NotBlank(message = "user.firstname.not_blank")
  @JsonProperty("first_name")
  private String firstName;

  @NotBlank(message = "user.lastname.not_blank")
  @JsonProperty("last_name")
  private String lastName;

  @NotBlank(message = "user.username.not_blank")
  private String username;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @NotBlank(message = "user.password.not_blank")
  @Size(min = 8, message = "Password must be at least 8 characters")
  private String password;

  @NotBlank(message = "user.email.not_blank")
  @Email(message = "user.email.invalid")
  private String email;

  private Timestamp createdAt;
  private Timestamp updatedAt;

  private Set<RoleDTO> roles;

  public User convertToEntity() {
    User user = new User();
    user.setId(this.id);
    user.setFirstName(this.firstName);
    user.setLastName(this.lastName);
    user.setUsername(this.username);
    user.setPassword(this.password);
    user.setEmail(this.email);
    user.setCreatedAt(this.createdAt);
    user.setUpdatedAt(this.updatedAt);
    if (this.roles != null && !this.roles.isEmpty()) {
      Set<UserRole> userRoles =
          this.roles.stream()
              .map(
                  role -> {
                    UserRole userRole = new UserRole();
                    userRole.setUser(user);
                    userRole.setRole(role.convertToEntity());
                    return userRole;
                  })
              .collect(Collectors.toSet());
      user.setRoles(userRoles);
    }
    return user;
  }

  public UserDTO convertToDTO(User user) {
    Set<RoleDTO> roleDTOs =
        user.getRoles().stream()
            .map(userRole -> userRole.getRole().convertToDTO())
            .collect(Collectors.toSet());

    return UserDTO.builder()
        .id(user.getId())
        .username(user.getUsername())
        .email(user.getEmail())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .createdAt(user.getCreatedAt())
        .updatedAt(user.getUpdatedAt())
        .roles(roleDTOs)
        .build();
  }
}
