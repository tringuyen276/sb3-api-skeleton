package com.digiex.utility.web.model.dto;

import com.digiex.utility.entity.Permission;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Getter
@ToString
@Builder
@Jacksonized
public class PermissionDTO {
  private Long id;
  @NotNull private String name;

  public static PermissionDTO convertToDto(Permission permission) {
    return PermissionDTO.builder().id(permission.getId()).name(permission.getName()).build();
  }
}
