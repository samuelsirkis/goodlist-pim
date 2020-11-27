package com.goodlist.domain.repositories;

import java.util.UUID;

import com.goodlist.domain.models.Provider;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderRepository extends JpaRepository<Provider, UUID> {

  Provider findByName(String name);

  Provider findByEmail(String email);
}
