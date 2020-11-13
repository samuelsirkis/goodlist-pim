package com.goodlist.domain.events;

import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

public class RecurseEvent extends ApplicationEvent {

  private static final long serialVersionUID = 1L;

  private HttpServletResponse resp;
  private UUID id;

  public RecurseEvent(Object source, HttpServletResponse resp, UUID uuid) {
    super(source);
    this.resp = resp;
    this.id = uuid;
  }

  public HttpServletResponse getResp() {
    return this.resp;
  }

  public UUID getId() {
    return this.id;
  }

}
