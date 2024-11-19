package com.digiex.utility.web.model.dto;

import com.digiex.utility.entity.RolePermission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionDto {
  private Long id;
  private Long roleId;
  private Long permissionId;

  public static RolePermissionDto convertToDto(RolePermission rolePermission) {
    return RolePermissionDto.builder()
        .id(rolePermission.getId())
        .roleId(rolePermission.getRole().getId())
        .permissionId(rolePermission.getPermission().getId())
        .build();
  }
}
