package com.goodlist.domain.repositories;

import java.util.UUID;

import com.goodlist.domain.models.OrderDetail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, UUID> {

}
