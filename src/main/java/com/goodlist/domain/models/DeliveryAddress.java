package com.goodlist.domain.models;

import java.util.UUID;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "delivery_address")
public class DeliveryAddress {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @NotNull
  private String street;

  private String complement;

  @NotNull
  private int number;

  @NotNull
  private String district;

  @NotNull
  private String city;

  @NotNull
  private String state;

  @NotNull
  private String zip_code;
}
