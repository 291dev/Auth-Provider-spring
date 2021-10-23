package com.auth.yf.security;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TokenBodyBean {

  String aud;
  String sub;
  String firstname;
  String tell;
  String iss;
  Long exp;
  Long iat;
  String[] authorities;
  String email;
}
