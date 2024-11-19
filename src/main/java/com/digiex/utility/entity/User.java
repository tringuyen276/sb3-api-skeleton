package com.digiex.utility.entity;

import jakarta.persistence.*;
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
  private UUID id;

  @Column(unique = true, nullable = false)
  private String username;

  @OneToMany(
      mappedBy = "user",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.EAGER)
  private Set<UserRole> userRoles = new HashSet<>();
}
