package com.goodlist.api.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.goodlist.api.service.CustomerService;
import com.goodlist.domain.enums.Status;
import com.goodlist.domain.events.RecurseEvent;
import com.goodlist.domain.models.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/customers")
public class CustomerController {
  @Autowired
  private CustomerService service;

  @Autowired
  private ApplicationEventPublisher publisher;

  @GetMapping
  public Page<Customer> listAll(Pageable pageable) {
    return service.findAll(pageable);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<?> getById(@PathVariable UUID id) {
    Customer customer = service.findById(id);
    return ResponseEntity.ok(customer);
  }

  @PostMapping
  public ResponseEntity<?> save(@Valid @RequestBody Customer customer, HttpServletResponse res) {
    Customer customerCreated = service.save(customer);
    publisher.publishEvent(new RecurseEvent(this, res, customerCreated.getId()));
    return ResponseEntity.status(HttpStatus.CREATED).body(customerCreated);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<?> updateById(@PathVariable UUID id, @RequestBody Customer customer) {
    Customer customerUpdate = service.updateById(id, customer);
    service.save(customer);
    return ResponseEntity.ok(customerUpdate);
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    service.delete(id);
  }

  @PutMapping(value = "/status/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateParsial(@PathVariable UUID id, @RequestBody Status status) {
    service.updateParsial(id, status);
  }
}
