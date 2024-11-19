package com.digiex.utility.web.model.dto;

import com.digiex.utility.entity.User;
import jakarta.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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

  @NotNull(message = "Username is required")
  private String username;

  @NotNull(message = "Roles is required")
  List<RoleDTO> roles;

  public User convertToEntity() {
    return User.builder().id(this.id).username(this.username).build();
  }

  public static UserDTO convertToDto(User user) {

    List<RoleDTO> roleDtos = new ArrayList<>();
    if (user.getUserRoles() != null) {
      /* roleDtos =
      user.getUserRoles().stream()
          .map(userRole -> RoleDTO.convertToDto(userRole.getRole()))
          .collect(Collectors.toList()); */
    }

    return UserDTO.builder().id(user.getId()).username(user.getUsername()).roles(roleDtos).build();
  }
}
