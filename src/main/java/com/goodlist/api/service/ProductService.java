package com.goodlist.api.service;

import java.util.UUID;

import com.goodlist.domain.exceptions.EntityNotFound;
import com.goodlist.domain.models.Product;
import com.goodlist.domain.repositories.ProductRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService {
  private static final String PRODUCT_NOT_FOUND = "Product not found";

  @Autowired
  private ProductRepository repository;

  public Product save(Product product) {
    return repository.save(product);
  }

  public Page<Product> getAll(Pageable pageable) {
    return repository.findAll(pageable);
  }

  public Product getById(UUID id) {
    return repository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
  }

  public Product updateById(UUID id, Product product) {
    Product productUpdate = getById(id);
    if (isValidproduct(productUpdate)) {
      BeanUtils.copyProperties(product, productUpdate, "id");
      return repository.save(productUpdate);
    }
    throw new EntityNotFound(PRODUCT_NOT_FOUND);
  }

  public void delete(UUID id) {
    Product product = repository.findById(id).orElseThrow(() -> new EntityNotFound(PRODUCT_NOT_FOUND));
    repository.delete(product);
  }

  private boolean isValidproduct(Product product) {
    if (product == null)
      throw new EmptyResultDataAccessException(1);
    return repository.findById(product.getId()).isPresent();
  }
}
