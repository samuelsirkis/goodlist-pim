package com.goodlist.domain.models;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
// import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
@Table(name = "products")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @NotNull
  @Size(min = 5, max = 50)
  private String name;

  @NotNull
  private String descripition;

  private double shipping_weight;

  @NotNull
  private double price;

  @UpdateTimestamp
  @CreatedDate
  @Column(columnDefinition = "TIMESTAMP")
  private LocalDateTime modified_at;

  @OneToOne
  @JoinColumn(name = "created_by")
  private Provider created_by;

  // @ManyToOne
  // @JoinColumn(name = "id")
  // private Category category_id;

  // private Set<OrderDetail> OrderDetails;
}
