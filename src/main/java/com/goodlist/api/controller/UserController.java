package com.goodlist.api.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.goodlist.api.service.UserService;
import com.goodlist.domain.events.RecurseEvent;
import com.goodlist.domain.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

  @Autowired
  private UserService service;

  @Autowired
  private ApplicationEventPublisher publisher;

  @GetMapping
  public Page<User> listAll(Pageable pageable) {
    return service.findAll(pageable);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<?> getById(@PathVariable UUID id) {
    User user = service.findById(id);
    return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
  }

  @PutMapping(value = "role/update")
  public ResponseEntity<?> update(@Valid @RequestBody User user, HttpServletResponse resp) {
    User userUpdate = service.update(user);
    publisher.publishEvent(new RecurseEvent(this, resp, userUpdate.getId()));
    return ResponseEntity.status(HttpStatus.CREATED).body(userUpdate);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<?> updateById(@Valid @RequestBody User user, @PathVariable UUID id) {
    User userUpdate = service.updateById(id, user);
    service.save(userUpdate);
    return ResponseEntity.ok(userUpdate);
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    service.delete(id);
  }
}
