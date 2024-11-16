package com.digiex.utility.web.model.dto;

import com.digiex.utility.web.model.Permission;
import com.digiex.utility.web.model.Role;
import com.digiex.utility.web.model.RolePermission;
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

    public RolePermission convertToEntity(Role role, Permission permission) {
        return RolePermission.builder()
                .id(this.id)
                .role(role)
                .permission(permission)
                .build();
    }

    public static RolePermissionDto convertToDto(RolePermission rolePermission) {
        return RolePermissionDto.builder()
                .id(rolePermission.getId())
                .roleId(rolePermission.getRole().getId())
                .permissionId(rolePermission.getPermission().getId())
                .build();
    }
}
