package com.digiex.utility.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "permission")
@Getter
@Setter
public class Permission {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "name", length = 50, nullable = false)
  private String name;

  @Column(name = "description", length = 255, nullable = false)
  private String description;

  @Column(name = "created_at", nullable = false, updatable = false)
  private Timestamp createdAt;

  @Column(name = "updated_at")
  private Timestamp updatedAt;

  @Column(name = "deleted_at")
  private Timestamp deletedAt;

  @ManyToMany(mappedBy = "permissions", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JsonIgnore
  private Set<Role> roles = new HashSet<>();
}
