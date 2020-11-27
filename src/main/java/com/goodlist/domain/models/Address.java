package com.goodlist.domain.models;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Embeddable
public class Address {
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
