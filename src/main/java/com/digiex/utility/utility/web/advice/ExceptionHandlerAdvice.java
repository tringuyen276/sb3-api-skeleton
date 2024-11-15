package com.digiex.utility.utility.web.advice;

import com.digiex.utility.utility.web.model.res.ApiResp;
import com.digiex.utility.utility.web.model.res.ErrorCode;
import com.digiex.utility.utility.web.model.res.FieldError;
import jakarta.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public abstract class ExceptionHandlerAdvice {
  @Getter private final MessageSource messageSource;

  public ExceptionHandlerAdvice(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  public List<FieldError> extractFieldErrors(BindingResult bindingResult) {
    List<FieldError> fieldErrors = Collections.emptyList();

    if (bindingResult.hasFieldErrors()) {
      fieldErrors =
          bindingResult.getFieldErrors().stream()
              .map(
                  error ->
                      FieldError.builder()
                          .field(error.getField())
                          .message(
                              messageSource.getMessage(
                                  error.getDefaultMessage(), null, LocaleContextHolder.getLocale()))
                          .build())
              .collect(Collectors.toList());
    }

    return fieldErrors;
  }

  public ResponseEntity<?> error(ErrorCode errorCode, List<FieldError> fieldErrors) {
    var message =
        messageSource.getMessage(errorCode.getSystemCode(), null, LocaleContextHolder.getLocale());

    var error =
        ApiResp.ErrorResp.builder().code(errorCode).message(message).details(fieldErrors).build();

    var response = ApiResp.builder().error(error).build();

    return ResponseEntity.status(error.getCode().getHttpStatusCode()).body(response);
  }

  public ResponseEntity<?> error(ErrorCode errorCode, Object... args) {
    var message =
        messageSource.getMessage(errorCode.getSystemCode(), args, LocaleContextHolder.getLocale());

    var error = ApiResp.ErrorResp.builder().code(errorCode).message(message).build();

    var response = ApiResp.builder().error(error).build();

    return ResponseEntity.status(error.getCode().getHttpStatusCode()).body(response);
  }

  public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e) {
    ApiResp.ErrorResp errorResp =
        ApiResp.ErrorResp.builder()
            .message("Validation failed")
            .details(
                e.getConstraintViolations().stream()
                    .map(
                        constraintViolation ->
                            constraintViolation.getPropertyPath().toString()
                                + " : "
                                + constraintViolation.getMessage())
                    .collect(Collectors.toList()))
            .build();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ApiResp.builder().error(errorResp).build());
  }
}
