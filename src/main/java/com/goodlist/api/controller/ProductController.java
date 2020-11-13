package com.goodlist.api.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.goodlist.api.service.ProductService;
import com.goodlist.domain.events.RecurseEvent;
import com.goodlist.domain.models.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

  @Autowired
  private ProductService service;

  ProductController(ProductService service) {
    this.service = service;
  }

  @Autowired
  private ApplicationEventPublisher publisher;

  @GetMapping
  public Page<Product> listAll(Pageable pageable) {
    return service.getAll(pageable);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getById(@PathVariable UUID id) {
    Product product = service.getById(id);
    return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
  }

  @PostMapping
  public ResponseEntity<?> create(@Valid @RequestBody Product product, HttpServletResponse res) {
    Product productSave = service.save(product);
    publisher.publishEvent(new RecurseEvent(this, res, productSave.getId()));
    return ResponseEntity.status(HttpStatus.CREATED).body(productSave);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<?> updateById(@Valid @RequestBody Product product, @PathVariable UUID id) {
    Product productUpdate = service.updateById(id, product);
    service.save(product);
    return ResponseEntity.ok(productUpdate);
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    service.delete(id);
  }
}
