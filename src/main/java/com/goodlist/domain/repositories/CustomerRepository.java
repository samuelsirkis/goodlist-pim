package com.goodlist.domain.repositories;

import java.util.UUID;

import com.goodlist.domain.models.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {

  Customer findByName(String name);

  Customer findByEmail(String email);

}
