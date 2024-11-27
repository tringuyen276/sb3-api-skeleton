package com.digiex.utility.web.model.dto;

import com.digiex.utility.entity.Permission;
import com.digiex.utility.entity.Role;
import com.digiex.utility.entity.RolePermission;
import jakarta.validation.constraints.NotBlank;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class RoleDTO {
  private Long id;

  @NotBlank(message = "role.name.not_blank")
  private String name;

  private Set<PermissionDTO> permissions;

  private Set<UserDTO> users;

  public Role convertToEntity() {
    Role role = new Role();
    role.setId(this.id);
    role.setName(this.name);
    if (this.permissions != null && !this.permissions.isEmpty()) {
      Set<RolePermission> rolePermissions =
          this.permissions.stream()
              .map(
                  permissionDTO -> {
                    Permission permission = permissionDTO.convertToEntity();
                    return RolePermission.builder().role(role).permission(permission).build();
                  })
              .collect(Collectors.toSet());
      role.setPermissions(rolePermissions);
    }
    return role;
  }

  public RoleDTO convertToDTO(Role role) {
    Set<PermissionDTO> permissionDTOs =
        role.getPermissions().stream()
            .map(rolePermission -> rolePermission.getPermission().convertToDTO())
            .collect(Collectors.toSet());

    return RoleDTO.builder().id(this.id).name(this.name).permissions(permissionDTOs).build();
  }
}
