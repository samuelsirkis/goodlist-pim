package com.goodlist.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {
  AwaitingPayment, ConfirmedPayment, OrderInProcessed, OrderDelivered, OrderConcluded
}
