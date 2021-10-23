package com.auth.yf.domain.exception;

public class RefleshTokenExpiredException extends Exception {
  public RefleshTokenExpiredException() {
  }

  public RefleshTokenExpiredException(String message) {
    super(message);
  }

  public RefleshTokenExpiredException(Throwable cause) {
    super(cause);
  }

  public RefleshTokenExpiredException(String message, Throwable cause) {
    super(message, cause);
  }

  public RefleshTokenExpiredException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
