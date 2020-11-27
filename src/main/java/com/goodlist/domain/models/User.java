package com.goodlist.domain.models;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.goodlist.domain.enums.Role;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(updatable = false, unique = true, nullable = false)
  private UUID id;

  @NotNull
  @Size(min = 3, max = 50)
  @Column(nullable = false, length = 100)
  private String name;

  @NotNull
  @Size(min = 5, max = 50)
  @Column(unique = true)
  private String email;

  @NotNull
  @Size(min = 1, max = 90)
  private String password;

  @Enumerated(EnumType.STRING)
  private Role role;

  @CreationTimestamp
  @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
  private LocalDateTime created_at;

  @UpdateTimestamp
  @CreatedDate
  @Column(columnDefinition = "TIMESTAMP")
  private LocalDateTime updated_at;

  public User(UUID id, String name, String email, String password) {
    this.setId(id);
    this.setName(name);
    this.setEmail(email);
    this.setPassword(password);
  }

  public boolean isAdmin() {
    return false;
  }

}