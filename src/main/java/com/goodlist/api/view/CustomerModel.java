package com.goodlist.api.view;

import java.time.LocalDateTime;
import java.util.UUID;

import com.goodlist.domain.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomerModel {
  private UUID id;
  private String name;
  private String email;
  private String phone;
  private Status status;

  private LocalDateTime created_at;

}
