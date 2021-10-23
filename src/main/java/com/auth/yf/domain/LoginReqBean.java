package com.auth.yf.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginReqBean {

  private String email;
  private String password;
}
