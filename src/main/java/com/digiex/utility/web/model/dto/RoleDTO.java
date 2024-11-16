package com.digiex.utility.web.model.dto;



import com.digiex.utility.web.model.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RoleDTO {
  private Long id;
  @NotNull(message = "Role is required")
  private String name;
  @NotNull(message = "Permission is required")
  private List<PermissionDTO> permissions;
  public Role convertToEntity() {
    return Role.builder()
            .id(this.id)
            .name(this.name)
            .build();
  }

  public static RoleDTO convertToDto(Role role) {
    List<PermissionDTO> permissionDtos= new ArrayList<>();
          if(role.getRolePermissions()!=null){
            permissionDtos  = role.getRolePermissions().stream()
                    .map(rolePermission -> PermissionDTO.convertToDto(rolePermission.getPermission()))
                    .collect(Collectors.toList());
          }
    return RoleDTO.builder()
            .id(role.getId())
            .name(role.getName())
            .permissions(permissionDtos)
            .build();
  }
}
