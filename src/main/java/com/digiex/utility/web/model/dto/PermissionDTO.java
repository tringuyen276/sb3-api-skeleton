package com.digiex.utility.web.model.dto;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PermissionDTO {
  private Integer id;
  private String name;
  private String description;
  private Timestamp createAt;

  // Constructor, getters và setters
}
