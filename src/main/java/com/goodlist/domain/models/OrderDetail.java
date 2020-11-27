package com.goodlist.domain.models;

import java.util.UUID;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "order_details")
public class OrderDetail {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  @NotNull
  private int quantity;

  private double amount;

  @OneToOne
  @JoinColumn(name = "order_id")
  private Order order;

  @OneToOne
  @JoinColumn(name = "payment_id")
  private Payment payment;
}
