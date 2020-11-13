package com.goodlist.domain.repositories;

import java.util.Optional;
import java.util.UUID;

import com.goodlist.domain.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

	Optional<User> findByEmailEquals(String email);

	User findByEmail(String email);

	Boolean existsByEmail(String email);
}
