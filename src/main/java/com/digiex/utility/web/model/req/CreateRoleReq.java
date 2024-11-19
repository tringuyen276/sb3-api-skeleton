package com.digiex.utility.web.model.req;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Getter
@ToString
@Builder
@Jacksonized
public class CreateRoleReq {
  @NotNull(message = "Role is required")
  private String name;

  @NotNull(message = "Permission is required")
  private List<Long> permissions;
}
