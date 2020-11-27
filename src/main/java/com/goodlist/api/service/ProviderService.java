package com.goodlist.api.service;

import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import com.goodlist.api.view.UpdatePassword;
import com.goodlist.domain.enums.Status;
import com.goodlist.domain.exceptions.EmailAlreadyExists;
import com.goodlist.domain.exceptions.EntityNotFound;
import com.goodlist.domain.interfaces.ProviderInterface;
import com.goodlist.domain.models.Provider;
import com.goodlist.domain.models.ProviderAddress;
import com.goodlist.domain.repositories.ProviderRepository;

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
public class ProviderService implements ProviderInterface {
  private static final String PROVIDER_NOT_FOUND = "Provider not found.";

  @Autowired
  private ProviderRepository repository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public Provider save(Provider provider) {
    checkEmailAvaliable(provider);
    provider.setPassword(passwordEncoder.encode(provider.getPassword()));
    return repository.save(provider);
  }

  public Provider update(Provider provider) {
    if (isValidProvider(provider))
      return repository.save(provider);
    throw new EntityNotFoundException();
  }

  public Provider findById(UUID id) {
    return repository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
  }

  public Page<Provider> findAll(Pageable pageable) {
    return repository.findAll(pageable);
  }

  public void delete(UUID id) {
    Provider provider = repository.findById(id).orElseThrow(() -> new EntityNotFound(PROVIDER_NOT_FOUND));
    repository.delete(provider);
  }

  @Override
  public Provider updatePassword(UpdatePassword password) {

    return null;
  }

  public Provider updateById(UUID id, Provider provider) {
    Provider providerUpdate = findById(id);
    if (isValidProvider(providerUpdate)) {
      BeanUtils.copyProperties(provider, providerUpdate, "id");
      return repository.save(providerUpdate);
    }
    throw new EntityNotFound(PROVIDER_NOT_FOUND);
  }

  public void updateParsial(UUID id, Status status) {
    Provider providerUpdate = findById(id);
    providerUpdate.setStatus(status);
    repository.save(providerUpdate);
  }

  public Provider updateAdress(UUID id, ProviderAddress address) {
    Provider providerUpdate = findById(id);
    if (isValidProvider(providerUpdate)) {
      BeanUtils.copyProperties(address, providerUpdate, "id");
      return repository.save(providerUpdate);
    }
    throw new EntityNotFound(PROVIDER_NOT_FOUND);
  }

  private void checkEmailAvaliable(Provider provider) {
    Provider providerFound = repository.findByEmail(provider.getEmail());
    if (providerFound != null && !provider.equals(providerFound)) {
      throw new EmailAlreadyExists("The Provider email already exists. Choose another provider email.");
    }
  }

  private boolean isValidProvider(Provider provider) {
    if (provider == null)
      throw new EmptyResultDataAccessException(1);
    return repository.findById(provider.getId()).isPresent();
  }
}
