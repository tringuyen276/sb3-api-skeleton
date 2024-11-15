package com.digiex.utility.web.model.dto;

import com.digiex.utility.web.model.Permission;
import jakarta.validation.constraints.NotBlank;
import java.sql.Timestamp;
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
public class PermissionDTO {
  private Integer id;

  @NotBlank(message = "permission.name.not_blank")
  private String name;

  private Timestamp createAt;

  public Permission convertToEntity() {
    Permission permission = new Permission();
    permission.setId(this.id);
    permission.setName(this.name);
    permission.setCreatedAt(this.createAt);
    return permission;
  }
}
