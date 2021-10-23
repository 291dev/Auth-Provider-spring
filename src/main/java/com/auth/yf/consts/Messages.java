package com.auth.yf.consts;

import lombok.Getter;

@Getter
public enum Messages {

  BAD_CREDENTIAL("Email or Password is incorrect"), USER_NOT_FOUND("User is not found. userName: {}"),
  USER_ALREADY_EXISTS("User already exists."), TOKEN_EXPIRED("Token expired"), TOKEN_INVALID("Token invalid"),
  REFLESH_TOKEN_EXPIRED("Reflesh token expired"), INCORRECT_ISSUER("Incollect issure"), INCORRECT_AUD("Incorrect aud"),
  UNEXPECTED("Unexpected error");

  private String message;

  private Messages(String message) {
    this.message = message;
  }

  public String getMessageWithFormat(Object element) {
    return String.format(this.message, element);
  }
}
