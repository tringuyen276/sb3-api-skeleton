package com.digiex.utility.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.*;
import java.util.UUID;
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

  @Column(name = "username", length = 20, nullable = false)
  private String username;

  @Column(name = "password", length = 60, nullable = false)
  @JsonIgnore
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

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinTable(
          name = "user_roles",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  private Set<Role> roles = new HashSet<>();
}
