package com.digiex.utility.web.model.res;

import com.digiex.utility.entity.Role;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class RoleRes {
  private Long id;
  private String name;
  private List<PermissionRes> permissions;

  public static RoleRes fromEntity(Role role) {
    var permissionResList =
        role.getRolePermissions().stream()
            .map(
                rolePermission -> {
                  var permission = rolePermission.getPermission();
                  return PermissionRes.builder()
                      .id(rolePermission.getId())
                      .name(permission.getName())
                      .build();
                })
            .collect(Collectors.toList());

    return RoleRes.builder().id(role.getId()).permissions(permissionResList).build();
  }
}
