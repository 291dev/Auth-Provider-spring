package com.auth.yf.entity;

import java.util.Date;
import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "app_users")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false, updatable = false)
  private Long id;
  private String firstName;
  private String lastName;
  private String userName;
  private String password;
  private String email;
  private String telephoneNumber;
  private String profileImageUrl;
  private Date birthOfDate;
  private OffsetDateTime lastLoginDate;
  private OffsetDateTime lastLoginDateDisplay;
  private OffsetDateTime joinDate;
  private String roles; // ROLE_USER{ read , edit }, ROLE_ADMIN { delete }
  private String[] authorities;
  private boolean isActive;
  private boolean isNotLocked;
  private boolean onLine;
}