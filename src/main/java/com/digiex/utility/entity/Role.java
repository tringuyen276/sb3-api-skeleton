package com.digiex.utility.entity;

import com.digiex.utility.web.model.dto.PermissionDTO;
import com.digiex.utility.web.model.dto.RoleDTO;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.*;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", updatable = false, nullable = false)
  private Long id;

  @Column(length = 20, unique = true, nullable = false)
  private String name;

  @OneToMany(mappedBy = "role", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private Set<UserRole> users;

  @OneToMany(mappedBy = "role", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private Set<RolePermission> permissions;

  @Column(name = "updated_at")
  private Timestamp updatedAt;

  @Column(name = "deleted_at")
  private Timestamp deletedAt;

  public RoleDTO convertToDTO() {

    Set<PermissionDTO> permissionDTOs =
        this.permissions.stream()
            .map(rolePermission -> rolePermission.getPermission().convertToDTO())
            .collect(Collectors.toSet());

    return RoleDTO.builder().id(this.id).name(this.name).permissions(permissionDTOs).build();
  }
}
