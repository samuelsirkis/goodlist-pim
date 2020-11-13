package com.goodlist.api.service;

import java.util.UUID;

import com.goodlist.domain.exceptions.EntityNotFound;
import com.goodlist.domain.exceptions.InexisteOrInativeException;
import com.goodlist.domain.interfaces.OrderInterface;
import com.goodlist.domain.models.Customer;
import com.goodlist.domain.models.Order;
import com.goodlist.domain.models.Provider;
import com.goodlist.domain.repositories.OrderRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderService implements OrderInterface {

	private static final String ORDER_NOT_FOUND = "Order not found";

	@Autowired
	private OrderRepository repository;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ProviderService providerService;

	public Order save(Order order) {
		Customer customer = customerService.findById(order.getCustomer().getId());
		if (customer == null || !customer.isActiveCustyomer(customer)) {
			throw new InexisteOrInativeException();
		}
		Provider provider = providerService.findById(order.getProvider().getId());
		if (provider == null || !provider.isActiveProvider(customer)) {
			throw new InexisteOrInativeException();
		}
		return repository.save(order);
	}

	public Page<Order> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public Order getById(UUID id) {
		return repository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
	}

	public Order updateById(UUID id, Order order) {
		Order orderUpdate = getById(id);
		if (isValidOrder(orderUpdate)) {
			BeanUtils.copyProperties(order, orderUpdate, "id");
			return repository.save(orderUpdate);
		}
		throw new EntityNotFound(ORDER_NOT_FOUND);
	}

	public void delete(UUID id) {
		Order order = repository.findById(id).orElseThrow(() -> new EntityNotFound(ORDER_NOT_FOUND));
		repository.delete(order);
	}

	// public Page<OrderFilter> filterOrder(OrderFilter orderFilter, Pageable
	// pageable) {
	// return repository.filterOrder(orderFilter);
	// }

	private boolean isValidOrder(Order order) {
		if (order == null)
			throw new EmptyResultDataAccessException(1);
		return repository.findById(order.getId()).isPresent();
	}
}
