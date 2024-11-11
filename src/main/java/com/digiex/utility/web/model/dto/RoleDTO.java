package com.digiex.utility.web.model.dto;

import com.digiex.utility.web.model.Permission;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RoleDTO {
  private Long id;
  @NotBlank(message = "Role name is required")
  private String name;
  private String description;
  private Set<PermissionDTO> permissions;
}
