package com.digiex.utility.web.advice;

import com.digiex.utility.exception.ApplicationErrorCode;
import com.digiex.utility.utility.web.advice.ExceptionHandlerAdvice;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@ControllerAdvice
public class ApplicationExceptionHandler extends ExceptionHandlerAdvice {
  public ApplicationExceptionHandler(
      @Qualifier("globalMessageSource") MessageSource messageSource) {
    super(messageSource);
  }

  @ExceptionHandler(Throwable.class)
  public ResponseEntity<?> handle(HttpServletRequest request, Throwable e) {
    log.error("{}", ExceptionUtils.getStackTrace(e));

    return error(ApplicationErrorCode.INTERNAL_ERROR_SERVER);
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<?> handle(
      HttpServletRequest request, HttpRequestMethodNotSupportedException e) {
    return error(ApplicationErrorCode.INVALID_HTTP_REQUEST_METHOD);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handle(HttpServletRequest request, MethodArgumentNotValidException e) {
    return error(
        ApplicationErrorCode.INVALID_REQUEST_PARAMETER, extractFieldErrors(e.getBindingResult()));
  }

  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<?> handle(HttpServletRequest request, NoResourceFoundException e) {
    return error(ApplicationErrorCode.INVALID_HTTP_REQUEST_RESOURCE, e.getResourcePath());
  }

  @ExceptionHandler(value = EntityNotFoundException.class)
  public ResponseEntity<?> handle(HttpServletRequest request, EntityNotFoundException e) {
    return error(ApplicationErrorCode.NOT_FOUND_HTTP_REQUEST_RESOURCE, e.getMessage());
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<?> handle(ConstraintViolationException e) {
    return error(ApplicationErrorCode.INVALID_REQUEST_PARAMETER, e.getMessage());
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<?> handle(HttpServletRequest request, DataIntegrityViolationException e) {
    return error(ApplicationErrorCode.CONFLICT_RESOURCE_ERROR, e.getMessage());
  }
}
