package com.goodlist.api.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.goodlist.domain.exceptions.InexisteOrInativeException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

  @Autowired
  private MessageSource messageSource;

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
      HttpStatus status, WebRequest request) {

    String messageUser = messageSource.getMessage("invalidation.message", null, LocaleContextHolder.getLocale());
    String messageDev = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
    List<Erro> errors = Arrays.asList(new Erro(messageUser, messageDev));
    return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
      HttpStatus status, WebRequest request) {
    List<Erro> errors = listErrors(ex.getBindingResult());
    return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler({ EmptyResultDataAccessException.class })
  public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex,
      WebRequest request) {
    String messageUser = messageSource.getMessage("recurse.not-found", null, LocaleContextHolder.getLocale());
    String messageDev = ex.toString();
    List<Erro> errors = Arrays.asList(new Erro(messageUser, messageDev));
    return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
  }

  @ExceptionHandler({ DataIntegrityViolationException.class })
  public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex,
      WebRequest request) {
    String messageUser = messageSource.getMessage("recurse.not-autorized", null, LocaleContextHolder.getLocale());
    String messageDev = ExceptionUtils.getRootCauseMessage(ex);
    List<Erro> errors = Arrays.asList(new Erro(messageUser, messageDev));
    return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler({ InexisteOrInativeException.class })
  public ResponseEntity<Object> handleStatusInativeException(InexisteOrInativeException ex, WebRequest request) {
    String messageUser = messageSource.getMessage("status.inexits-or-inative", null, LocaleContextHolder.getLocale());
    String messageDev = ExceptionUtils.getRootCauseMessage(ex);
    List<Erro> errors = Arrays.asList(new Erro(messageUser, messageDev));
    return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  private List<Erro> listErrors(BindingResult bindingResult) {
    List<Erro> errors = new ArrayList<>();

    for (FieldError fieldError : bindingResult.getFieldErrors()) {
      String messageUser = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
      String messageDev = fieldError.toString();
      errors.add(new Erro(messageUser, messageDev));
    }
    return errors;
  }

  public static class Erro {
    private String messageUser;
    private String messageDev;

    public Erro(String messageUser, String messageDev) {
      this.messageUser = messageUser;
      this.messageDev = messageDev;
    }

    public String getMessageUser() {
      return messageUser;
    }

    public String getMessageDev() {
      return messageDev;
    }
  }
}