package com.auth.yf.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterReqBean {

  private String firstName;
  private String lastName;
  private String password;
  private String email;
  private String telephoneNumber;
  private Date dateOfBirth;
}
