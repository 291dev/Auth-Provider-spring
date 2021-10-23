package com.auth.yf.domain;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserDomain {
  private String firstName;
  private String lastName;
  private String userName;
  private String password;
  private String email;
  private String telephoneNumber;
  private String profileImageUrl;
  private Date birthOfDate;
  private Timestamp lastLoginDateTime;
  private Timestamp joinDateTime;
  private String roles; // ROLE_USER{ read , edit }, ROLE_ADMIN { delete }
  private String[] authorities;
  private boolean isActive;
  private boolean isNotLocked;
  private boolean onLine;
}
