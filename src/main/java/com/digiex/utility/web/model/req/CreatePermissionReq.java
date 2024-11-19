package com.digiex.utility.web.model.req;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Getter
@ToString
@Builder
@Jacksonized
public class CreatePermissionReq {
  @NotNull private String name;
}
