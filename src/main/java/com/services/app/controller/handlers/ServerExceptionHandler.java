package com.services.app.controller.handlers;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.services.app.exceptions.ServerException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import java.sql.SQLException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ServerExceptionHandler {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(ConstraintViolationException.class)
  public @ResponseBody Map<String, List<String>> handleConstraintViolation(ConstraintViolationException e,
      ServletWebRequest request) {
    // emulate Spring DefaultErrorAttributes
    log.warn("---->{} -- {}", request.getRequest().getRequestURI(), e.getMessage());

    final Map<String, List<String>> result = new LinkedHashMap<>();
    final List<String> err = new ArrayList<String>();
    err.add("Обьект с таким значением уже существует");
    result.put("errors", err);
    return result;
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(ServerException.class)
  public @ResponseBody Map<String, List<String>> handleServerException(ServerException e,
      ServletWebRequest request) {

    // emulate Spring DefaultErrorAttributes
    log.warn("---->{} -- {}", request.getRequest().getRequestURI(), e.getMessage());

    final Map<String, List<String>> result = new LinkedHashMap<>();
    result.put("errors", e.getError());

    // result.put(e.getError());

    return result;
  }

  // IllegalArgumentException

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(EmptyResultDataAccessException.class)
  public @ResponseBody Map<String, List<String>> handleEmptyResultDataAccessException(EmptyResultDataAccessException e,
      ServletWebRequest request) {
    // emulate Spring DefaultErrorAttributes
    log.warn("---->{} -- {}", request.getRequest().getRequestURI(), e.getMessage());

    final Map<String, List<String>> result = new LinkedHashMap<>();
    final List<String> err = new ArrayList<String>();
    err.add("Обьект с таким идентификатором не существует");
    result.put("errors", err);

    return result;
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(SQLException.class)
  public @ResponseBody Map<String, List<String>> handleSqlException(SQLException e,
      ServletWebRequest request) {
    // emulate Spring DefaultErrorAttributes
    log.warn("---->{} -- {}", request.getRequest().getRequestURI(), e.getMessage());

    final Map<String, List<String>> result = new LinkedHashMap<>();
    final List<String> err = new ArrayList<String>();
    if (e.getMessage().startsWith("ORA-12899", 0)) {
      err.add("Значение поля превышает максимальное число символов!");
    } else {
      err.add("Значения отклонены базой данных!");
    }

    result.put("errors", err);

    return result;
  }

}