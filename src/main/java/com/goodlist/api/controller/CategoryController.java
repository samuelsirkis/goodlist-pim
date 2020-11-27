package com.goodlist.api.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.goodlist.api.service.CategoryService;
import com.goodlist.domain.events.RecurseEvent;
import com.goodlist.domain.models.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

  @Autowired
  private CategoryService service;

  CategoryController(CategoryService service) {
    this.service = service;
  }

  @Autowired
  private ApplicationEventPublisher publisher;

  @GetMapping
  public Page<?> listAll(Pageable pageable) {
    return service.findAll(pageable);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getById(@PathVariable UUID id) {
    Category category = service.getById(id);
    return category != null ? ResponseEntity.ok(category) : ResponseEntity.notFound().build();
  }

  @PostMapping
  public ResponseEntity<?> create(@Valid @RequestBody Category category, HttpServletResponse res) {
    Category categorySave = service.save(category);
    publisher.publishEvent(new RecurseEvent(this, res, categorySave.getId()));
    return ResponseEntity.status(HttpStatus.CREATED).body(categorySave);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<?> updateById(@Valid @RequestBody Category category, @PathVariable UUID id) {
    Category categoryUpdate = service.updateById(id, category);
    service.save(category);
    return ResponseEntity.ok(categoryUpdate);
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    service.delete(id);
  }
}
