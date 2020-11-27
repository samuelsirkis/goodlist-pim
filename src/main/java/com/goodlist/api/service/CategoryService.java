package com.goodlist.api.service;

import java.util.UUID;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import com.goodlist.domain.exceptions.EntityNotFound;
import com.goodlist.domain.interfaces.CategoryInterface;
import com.goodlist.domain.models.Category;
import com.goodlist.domain.repositories.CategoryRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryService implements CategoryInterface {

  private static final String CATEGORY_NOT_FOUND = "Category not found";

  @Autowired
  private CategoryRepository repository;

  public Category save(Category category) {
    return repository.save(category);
  }

  public Page<Category> findAll(Pageable pageable) {
    return repository.findAll(pageable);
  }

  public Category getById(UUID id) {
    return repository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
  }

  public Category update(@Valid Category category) {
    if (isValidCategory(category))
      return repository.save(category);
    throw new EntityNotFoundException();
  }

  public Category updateById(UUID id, Category category) {
    Category categoryUpdate = getById(id);
    if (isValidCategory(categoryUpdate)) {
      BeanUtils.copyProperties(category, categoryUpdate, "id");
      return repository.save(categoryUpdate);
    }
    throw new EntityNotFound(CATEGORY_NOT_FOUND);
  }

  public void delete(UUID id) {
    Category category = repository.findById(id).orElseThrow(() -> new EntityNotFound(CATEGORY_NOT_FOUND));
    repository.delete(category);
  }

  private boolean isValidCategory(Category category) {
    if (category == null)
      throw new EmptyResultDataAccessException(1);
    return repository.findById(category.getId()).isPresent();
  }
}
