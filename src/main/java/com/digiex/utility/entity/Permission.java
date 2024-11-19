package com.digiex.utility.entity;

import jakarta.persistence.*;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "permissions")
public class Permission {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column private String name;

  @OneToMany(mappedBy = "permission", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private Set<RolePermission> rolePermissions;
}
