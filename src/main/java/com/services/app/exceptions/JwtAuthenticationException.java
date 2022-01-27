package com.services.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import lombok.Getter;

@Getter
public class JwtAuthenticationException extends AuthenticationException {

  private HttpStatus httpStatus;

  public JwtAuthenticationException(String msg, HttpStatus httpStatus) {
    super(msg);
    this.httpStatus = httpStatus;
  }

  public JwtAuthenticationException(String msg) {
    super(msg);
  }
}
