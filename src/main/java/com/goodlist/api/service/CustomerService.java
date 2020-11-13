package com.goodlist.api.service;

import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import com.goodlist.api.view.UpdatePassword;
import com.goodlist.domain.enums.Status;
import com.goodlist.domain.exceptions.EmailAlreadyExists;
import com.goodlist.domain.exceptions.EntityNotFound;
import com.goodlist.domain.interfaces.CustomerInterface;
import com.goodlist.domain.models.Address;
import com.goodlist.domain.models.Customer;
import com.goodlist.domain.repositories.CustomerRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerService implements CustomerInterface {

	private static final String CUSTOMER_NOT_FOUND = "Customer not found.";

	@Autowired
	private CustomerRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Customer save(Customer customer) {
		checkEmailAvaliable(customer);
		customer.setPassword(passwordEncoder.encode(customer.getPassword()));
		return repository.save(customer);
	}

	@Override
	public Customer update(Customer customer) {
		if (isValidCustomer(customer))
			return repository.save(customer);
		throw new EntityNotFoundException();
	}

	@Override
	public Customer findById(UUID id) {
		return repository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
	}

	@Override
	public Page<Customer> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	public void delete(UUID id) {
		Customer customer = repository.findById(id).orElseThrow(() -> new EntityNotFound(CUSTOMER_NOT_FOUND));
		repository.delete(customer);
	}

	@Override
	public Customer updatePassword(UpdatePassword password) {

		return null;
	}

	public Customer updateById(UUID id, Customer customer) {
		Customer customerUpdate = findById(id);
		if (isValidCustomer(customerUpdate)) {
			BeanUtils.copyProperties(customer, customerUpdate, "id");
			return repository.save(customerUpdate);
		}
		throw new EntityNotFound(CUSTOMER_NOT_FOUND);
	}

	public void updateParsial(UUID id, Status status) {
		Customer customerUpdate = findById(id);
		customerUpdate.setStatus(status);
		repository.save(customerUpdate);
	}

	public Customer updateAdress(UUID id, Address address) {
		Customer customerUpdate = findById(id);
		if (isValidCustomer(customerUpdate)) {
			BeanUtils.copyProperties(address, customerUpdate, "id");
			return repository.save(customerUpdate);
		}
		throw new EntityNotFound(CUSTOMER_NOT_FOUND);
	}

	private void checkEmailAvaliable(Customer customer) {
		Customer customerFound = repository.findByEmail(customer.getEmail());
		if (customerFound != null && !customer.equals(customerFound)) {
			throw new EmailAlreadyExists("The Customer email already exists. Choose another Customer email.");
		}
	}

	private boolean isValidCustomer(Customer customer) {
		if (customer == null)
			throw new EmptyResultDataAccessException(1);
		return repository.findById(customer.getId()).isPresent();
	}
}
