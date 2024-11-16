package com.digiex.utility.web.model.dto;

import java.sql.Timestamp;

import com.digiex.utility.web.model.Permission;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PermissionDTO {
  private Long id;
  @NotNull
  private String name;

  public Permission convertToEntity() {
    return Permission.builder()
            .id(this.id)
            .name(this.name)
            .build();
  }

  public static PermissionDTO convertToDto(Permission permission) {
    return PermissionDTO.builder()
            .id(permission.getId())
            .name(permission.getName())
            .build();
  }
}
