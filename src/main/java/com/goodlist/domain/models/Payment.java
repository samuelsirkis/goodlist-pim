package com.goodlist.domain.models;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.*;

import com.goodlist.domain.enums.PaymentStatus;

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
@Table(name = "payments")
public class Payment {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  private String type;
  private double amount;
  private String card_number;
  private String first_name;
  private String last_name;
  private String expiration_date;
  private String security_code;
  private String brand;
  private boolean save_card;

  @CreationTimestamp
  @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
  private LocalDateTime created_at;

  @Enumerated(EnumType.STRING)
  private PaymentStatus payment_status;

  @UpdateTimestamp
  @CreatedDate
  @Column(columnDefinition = "TIMESTAMP")
  private LocalDateTime processed_at;

  // @OneToOne
  // @JoinColumn(name = "order_id")
  // private Order order;
}
