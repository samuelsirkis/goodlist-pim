package com.goodlist.api.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.goodlist.api.service.ProviderService;
import com.goodlist.domain.enums.Status;
import com.goodlist.domain.events.RecurseEvent;
import com.goodlist.domain.models.Provider;

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
@RequestMapping(value = "/providers")
public class ProviderController {

  @Autowired
  private ProviderService service;

  @Autowired
  private ApplicationEventPublisher publisher;

  @GetMapping
  public Page<Provider> listAll(Pageable pageable) {
    return service.findAll(pageable);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<?> getById(@PathVariable UUID id) {
    Provider provider = service.findById(id);
    return ResponseEntity.ok(provider);
  }

  @PostMapping
  public ResponseEntity<?> save(@Valid @RequestBody Provider provider, HttpServletResponse res) {
    Provider provideCreated = service.save(provider);
    publisher.publishEvent(new RecurseEvent(this, res, provideCreated.getId()));
    return ResponseEntity.status(HttpStatus.CREATED).body(provideCreated);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<?> updateById(@PathVariable UUID id, @RequestBody Provider provider) {
    Provider providerUpdate = service.updateById(id, provider);
    service.save(provider);
    return ResponseEntity.ok(providerUpdate);
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
