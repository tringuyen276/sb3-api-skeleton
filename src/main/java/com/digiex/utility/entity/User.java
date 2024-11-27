package com.digiex.utility.entity;

import com.digiex.utility.web.model.dto.RoleDTO;
import com.digiex.utility.web.model.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import lombok.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Getter
@Setter
public class User {
  @Id
  @GeneratedValue(generator = "UUID")
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;

  @Column(name = "username", length = 20, nullable = false, unique = true)
  private String username;

  @Column(name = "password", length = 60, nullable = false)
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;

  @Column(name = "email", length = 50, nullable = false)
  private String email;

  @Column(name = "first_name", length = 50, nullable = false)
  private String firstName;

  @Column(name = "last_name", length = 50, nullable = false)
  private String lastName;

  @Column(name = "created_at")
  private Timestamp createdAt;

  @Column(name = "updated_at")
  private Timestamp updatedAt;

  @Column(name = "deleted_at")
  @JsonIgnore
  private Timestamp deletedAt;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
  private Set<UserRole> roles;

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(id, user.id);
  }

  public UserDTO convertToDTO() {
    Set<RoleDTO> roleDTOs =
        this.roles.stream()
            .map(userRole -> userRole.getRole().convertToDTO())
            .collect(Collectors.toSet());

    return UserDTO.builder()
        .id(this.id)
        .username(this.username)
        .email(this.email)
        .firstName(this.firstName)
        .lastName(this.lastName)
        .createdAt(this.createdAt)
        .updatedAt(this.updatedAt)
        .roles(roleDTOs)
        .build();
  }
}
