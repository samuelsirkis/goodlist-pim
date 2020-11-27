package com.goodlist.domain.repositories;

import java.util.UUID;

import com.goodlist.domain.models.Payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {

}
