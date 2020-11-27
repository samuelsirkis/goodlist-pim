package com.goodlist.domain.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.goodlist.domain.enums.Status;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "customers")
public class Customer {

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
  private String phone;

  @NotNull
  private String password;

  @NotNull
  private String cpf;

  private LocalDate birthdate;

  @Enumerated(EnumType.STRING)
  @ColumnDefault("ACTIVE")
  private Status status;

  @CreationTimestamp
  @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
  private LocalDateTime created_at;

  @UpdateTimestamp
  @CreatedDate
  @Column(columnDefinition = "TIMESTAMP")
  private LocalDateTime updated_at;

  @Embedded
  private Address address;

  @OneToOne
  @JoinColumn(name = "id")
  private DeliveryAddress delivery_address;

  @JsonIgnore
  @Transient
  public boolean isActiveCustyomer(Customer customer) {
    if (customer.getStatus() == Status.ACTIVE)
      return true;
    return false;
  }
}
