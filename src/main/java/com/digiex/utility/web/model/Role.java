package com.digiex.utility.web.model;
import jakarta.persistence .*;
import lombok.*;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String name;

  @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
  private Set<UserRole> userRoles = new HashSet<>();

  @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
  private Set<RolePermission> rolePermissions = new HashSet<>();



}
