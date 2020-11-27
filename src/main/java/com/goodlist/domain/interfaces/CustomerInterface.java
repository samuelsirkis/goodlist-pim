package com.goodlist.domain.interfaces;

import java.util.UUID;

import com.goodlist.api.view.UpdatePassword;
import com.goodlist.domain.models.Customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerInterface {

  Customer save(Customer customer);

  Customer update(Customer customer);

  Customer findById(UUID id);

  Page<Customer> findAll(Pageable pageable);

  void delete(UUID id);

  Customer updatePassword(UpdatePassword password);
}
