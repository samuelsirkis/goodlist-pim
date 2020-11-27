package com.goodlist.domain.models;

// import java.util.ArrayList;
// import java.util.List;
import java.util.UUID;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

// import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;

@Entity
@Table(name = "categories")
@EqualsAndHashCode(of = "id")
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @NotNull
  @Size(min = 5, max = 50)
  private String name;

  // @JsonIgnore
  // @OneToMany(mappedBy = "category")
  // private List<Product> products = new ArrayList<>();

  public UUID getId() {
    return this.id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  // public List<Product> getProducts() {
  // return products;
  // }
}
