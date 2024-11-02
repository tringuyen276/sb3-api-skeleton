package com.digiex.utility.web.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "permission")
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

  public Permission() {}

  public Permission(Integer id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
  }
}
