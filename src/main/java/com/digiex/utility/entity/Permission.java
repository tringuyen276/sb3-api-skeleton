package com.digiex.utility.entity;

import com.digiex.utility.web.model.dto.PermissionDTO;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "permissions")
@Getter
@Setter
public class Permission {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "name", length = 50, nullable = false)
  private String name;

  @Column(name = "created_at", nullable = false, updatable = false)
  private Timestamp createdAt;

  @Column(name = "updated_at")
  private Timestamp updatedAt;

  @Column(name = "deleted_at")
  private Timestamp deletedAt;

  @OneToMany(mappedBy = "permission")
  private Set<RolePermission> roles;

  public PermissionDTO convertToDTO() {
    return PermissionDTO.builder().id(this.id).name(this.name).createAt(this.createdAt).build();
  }
}
