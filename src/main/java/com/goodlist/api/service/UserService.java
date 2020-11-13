package com.goodlist.api.service;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import com.goodlist.api.view.UpdatePassword;
import com.goodlist.core.jwt.JwtUserFactory;
import com.goodlist.domain.enums.Role;
import com.goodlist.domain.exceptions.ActionNotAllowed;
import com.goodlist.domain.exceptions.EmailAlreadyExists;
import com.goodlist.domain.exceptions.EntityNotFound;
import com.goodlist.domain.exceptions.PasswordNotMatchException;
import com.goodlist.domain.interfaces.UserInterface;
import com.goodlist.domain.models.User;
import com.goodlist.domain.repositories.UserRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements UserInterface, UserDetailsService {

  private static final String USER_NOT_FOUND = "User not found";

  @Autowired
  private UserRepository repository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public User save(User user) {
    checkUserEmailAvailable(user);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return repository.save(user);
  }

  public Page<User> findAll(Pageable pageable) {
    return repository.findAll(pageable);
  }

  @Override
  public User findById(UUID id) {
    User user = repository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
    return user;
  }

  @Override
  public User updatePassword(UpdatePassword user) {
    if (!user.getNewPassword().equals(user.getConfirmPassword())) {
      throw new PasswordNotMatchException("The new password and confirm password not match");
    }

    User userFound = repository.findByEmail(user.getEmail());
    if (userFound == null)
      throw new EntityNotFound(USER_NOT_FOUND);

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    if (!passwordEncoder.matches(user.getPassword(), userFound.getPassword())) {
      throw new ActionNotAllowed("You don't have permission to update the password this user");
    }

    userFound.setPassword(user.getNewPassword());
    return save(userFound);
  }

  public User update(User user) {
    if (isValidUser(user))
      return repository.save(user);
    throw new EntityNotFound(USER_NOT_FOUND);
  }

  public User updateById(UUID id, User user) {
    User userUpdate = findById(id);
    if (isValidUser(userUpdate)) {
      BeanUtils.copyProperties(user, userUpdate, "id");
      return repository.save(userUpdate);
    }
    throw new EntityNotFound(USER_NOT_FOUND);
  }

  public void delete(UUID id) {
    User user = repository.findById(id).orElseThrow(() -> new EntityNotFound(USER_NOT_FOUND));
    repository.delete(user);
  }

  public User register(User object) {
    System.out.println(object);
    Optional<User> exists = repository.findByEmailEquals(object.getEmail());
    if (exists.isPresent() && !exists.get().equals(object)) {
      throw new EmailAlreadyExists("Email já cadastrado.");
    }
    object.setRole(Role.USER);
    return repository.save(object);
  }

  public Optional<User> findByEmail(String email) {
    return repository.findByEmailEquals(email);
  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<User> user = repository.findByEmailEquals(email);
    if (user.isPresent())
      return JwtUserFactory.build(user.get());
    throw new UsernameNotFoundException("Email não encontrado.");
  }

  private boolean isValidUser(User user) {
    if (user == null)
      throw new EmptyResultDataAccessException(1);
    return repository.findById(user.getId()).isPresent();
  }

  private void checkUserEmailAvailable(User user) {
    User userFound = repository.findByEmail(user.getEmail());
    if (userFound != null && !user.equals(userFound)) {
      throw new EmailAlreadyExists("The user email already exists. Choose another user email.");
    }
  }

}
