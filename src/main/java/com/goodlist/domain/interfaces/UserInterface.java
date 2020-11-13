package com.goodlist.domain.interfaces;

import java.util.UUID;

import com.goodlist.api.view.UpdatePassword;
import com.goodlist.domain.models.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserInterface {
  User save(User user);

  User update(User user);

  User findById(UUID id);

  Page<User> findAll(Pageable pageable);

  User updatePassword(UpdatePassword user);
}
