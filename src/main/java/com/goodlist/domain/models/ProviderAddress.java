package com.goodlist.domain.models;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Embeddable
public class ProviderAddress {
  @NotNull
  private String address;

  @NotNull
  private String city;

  @NotNull
  private String state;

  @NotNull
  private String zip_code;
}
