package com.digiex.utility.web.model;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Role {

  @Id
  @GeneratedValue(generator = "UUID")
  @Column(name = "id", columnDefinition = "uuid", updatable = false, nullable = false)
  private UUID id; // Change from Integer to UUID

  @Column(length = 20, unique = true, nullable = false)
  private String name;

  @Column(length = 255, nullable = false)
  private String description;

  @ManyToMany(mappedBy = "roles")
  private Set<User> users;

  @ManyToMany
  @JoinTable(
      name = "role_permissions",
      joinColumns = @JoinColumn(name = "role_id"),
      inverseJoinColumns = @JoinColumn(name = "access_id")) // Ensure
  // this
  // matches
  // your
  // schema
  private Set<Permission> permissions;

  @Column(name = "created_at")
  private Timestamp createdAt;

  @Column(name = "updated_at")
  private Timestamp updatedAt;

  @Column(name = "deleted_at")
  private Timestamp deletedAt;
}
