package com.digiex.utility.web.model.res;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class PermissionRes {
  private Long id;
  private String name;
}
