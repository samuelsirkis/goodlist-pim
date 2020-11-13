package com.goodlist.domain.repositories;

import java.util.UUID;

import com.goodlist.domain.models.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

}
