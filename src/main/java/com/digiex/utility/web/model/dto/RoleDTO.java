package com.digiex.utility.web.model.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;
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
public class RoleDTO {
  private Long id;

  @NotNull(message = "Role is required")
  private String name;

  @NotNull(message = "Permission is required")
  private List<Long> permissions;
}
