package com.goodlist.domain.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.*;
import javax.validation.constraints.Size;

import com.goodlist.domain.enums.OrderStatus;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "orders")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Size(min = 5, max = 100)
  private String decription;

  private LocalDate due_date;
  private LocalDate payment_date;
  private double price;

  @Size(min = 5, max = 100)
  private String observation;

  @CreationTimestamp
  @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
  private LocalDateTime order_date;

  @Enumerated(EnumType.STRING)
  @ColumnDefault("AwaitingPayment")
  private OrderStatus order_status;

  @OneToOne
  @JoinColumn(name = "customer_id")
  private Customer customer;

  @OneToOne
  @JoinColumn(name = "provider_id")
  private Provider provider;

  // private Set<OrderDetail> order_details;

  // @OneToOne
  // @JoinColumn(name = "payment_id")
  // private Payment payment;

}