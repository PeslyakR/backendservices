package com.services.app.exceptions;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServerException extends Exception {

  List<String> error = new ArrayList<String>();

  public ServerException(String message) {
    super();

    error.add(message);
  }

}
