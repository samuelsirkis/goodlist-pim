package com.goodlist.domain.events.listener;

import java.net.URI;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import com.goodlist.domain.events.RecurseEvent;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class RecurseListener implements ApplicationListener<RecurseEvent> {

  @Override
  public void onApplicationEvent(RecurseEvent event) {
    HttpServletResponse resp = event.getResp();
    UUID id = event.getId();
    addHeaderLocation(resp, id);
  }

  private void addHeaderLocation(HttpServletResponse resp, UUID id) {
    URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(id).toUri();

    resp.setHeader("Location", uri.toASCIIString());
  }
}
