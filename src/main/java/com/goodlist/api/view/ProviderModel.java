package com.goodlist.api.view;

import java.time.LocalDateTime;
import java.util.UUID;

import com.goodlist.domain.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProviderModel {
  private UUID id;
  private String name;
  private String responsible;
  private String email;
  private String phone;
  private String cnpj;
  private Status status;

  private LocalDateTime created_at;

}
