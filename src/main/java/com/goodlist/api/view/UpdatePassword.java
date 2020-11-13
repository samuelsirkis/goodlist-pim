package com.goodlist.api.view;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;

@Getter
public class UpdatePassword {
  @NotNull
  @NotBlank
  private String name;

  @NotNull
  @NotBlank
  private String password;

  @NotNull
  @NotBlank
  private String newPassword;

  @NotNull
  @NotBlank
  private String confirmPassword;

  public String getEmail() {
    return null;
  }
}
