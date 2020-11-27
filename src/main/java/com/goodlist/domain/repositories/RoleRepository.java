package com.goodlist.domain.repositories;

import java.util.Optional;

import com.goodlist.domain.enums.Role;
import com.goodlist.domain.models.UserRole;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<UserRole, Long> {
  Optional<Role> findByName(Role name);
}
