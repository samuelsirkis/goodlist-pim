package com.goodlist.api.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.goodlist.api.service.OrderService;
import com.goodlist.domain.events.RecurseEvent;
import com.goodlist.domain.models.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

  @Autowired
  private OrderService service;

  OrderController(OrderService service) {
    this.service = service;
  }

  @Autowired
  private ApplicationEventPublisher publisher;

  @GetMapping
  public Page<?> listAll(Pageable pageable) {
    return service.findAll(pageable);
  }

  // @GetMapping("/filter")
  // public Page<?> filter(OrderFilter orderFilter, Pageable pageable) {
  // return service.filterOrder(orderFilter, pageable);
  // }

  @GetMapping("/{id}")
  public ResponseEntity<?> getById(@PathVariable UUID id) {
    Order order = service.getById(id);
    return order != null ? ResponseEntity.ok(order) : ResponseEntity.notFound().build();
  }

  @PostMapping
  public ResponseEntity<?> create(@Valid @RequestBody Order order, HttpServletResponse res) {
    Order orderSave = service.save(order);
    publisher.publishEvent(new RecurseEvent(this, res, orderSave.getId()));
    return ResponseEntity.status(HttpStatus.CREATED).body(orderSave);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<?> updateById(@Valid @RequestBody Order order, @PathVariable UUID id) {
    Order orderUpdate = service.updateById(id, order);
    service.save(order);
    return ResponseEntity.ok(orderUpdate);
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    service.delete(id);
  }
}
