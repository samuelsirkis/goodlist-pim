package com.goodlist.domain.interfaces;

import java.util.UUID;

import com.goodlist.api.view.UpdatePassword;
import com.goodlist.domain.models.Provider;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProviderInterface {
  Provider save(Provider provider);

  Provider update(Provider provider);

  Provider findById(UUID id);

  Page<Provider> findAll(Pageable pageable);

  void delete(UUID id);

  Provider updatePassword(UpdatePassword password);
}
