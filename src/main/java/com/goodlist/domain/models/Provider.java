package com.goodlist.domain.models;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.goodlist.domain.enums.Status;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "providers")
public class Provider {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @NotNull
  @Size(min = 5, max = 50)
  private String name;

  @NotNull
  @Size(min = 10, max = 50)
  @Column(unique = true)
  private String email;

  @NotNull
  private String responsible;

  @NotNull
  private String phone;

  @NotNull
  private String password;

  @NotNull
  private String cnpj;

  @Enumerated(EnumType.STRING)
  @ColumnDefault("ACTIVE")
  @JsonInclude()
  private Status status;

  @CreationTimestamp
  @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
  private LocalDateTime created_at;

  @UpdateTimestamp
  @CreatedDate
  @Column(columnDefinition = "TIMESTAMP")
  private LocalDateTime updated_at;

  @Embedded
  private ProviderAddress address;

  @JsonIgnore
  public boolean isActiveProvider(Customer customer) {
    if (customer.getStatus() == Status.ACTIVE)
      return true;
    return false;
  }
}
