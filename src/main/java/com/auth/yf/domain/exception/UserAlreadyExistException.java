package com.auth.yf.domain.exception;

public class UserAlreadyExistException extends Exception {

  public UserAlreadyExistException() {
  }

  public UserAlreadyExistException(String message) {
    super(message);
  }

  public UserAlreadyExistException(Throwable cause) {
    super(cause);
  }

  public UserAlreadyExistException(String message, Throwable cause) {
    super(message, cause);
  }

  public UserAlreadyExistException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
