package com.goodlist.domain.repositories;

import java.util.UUID;

import com.goodlist.domain.models.Order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, UUID> {

}
