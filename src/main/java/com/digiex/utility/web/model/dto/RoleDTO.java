package com.digiex.utility.web.model.dto;

import com.digiex.utility.web.model.Permission;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RoleDTO {
    private Long id;
    private String name;
    private String description;
    private Set<Permission> permissions;
}
