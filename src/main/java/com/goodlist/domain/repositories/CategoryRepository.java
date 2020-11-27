package com.goodlist.domain.repositories;

import java.util.Optional;
import java.util.UUID;

import com.goodlist.domain.models.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

  Optional<Category> findById(UUID id);

}
