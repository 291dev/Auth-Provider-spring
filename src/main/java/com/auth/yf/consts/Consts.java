package com.auth.yf.consts;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "com.auth.yf")
public class Consts {
  public static final String TOKEN_HEADER = "Bearer ";
  public static final String JWT_ACCESS_TOKEN_HEADER = "Jwt-Access-Token";
  public static final String JWT_ID_TOKEN_HEADER = "Jwt-Id-Token";
  public static final String JWT_REFLESH_TOKEN_HEADER = "Jwt-Reflesh-Token";
  public static final String AUTHORITY_CLAIM = "authorities";
  public static final String BIRTHDAY_CLAIM = "birthday";
  public static final String TOKEN_SPECIES = "token-species";
  public static final String TEL_CLAIM = "tell";
  public static final String EMAIL = "email";
  public static final String FIRSTNAME = "firstname";
  public static final String LASTNAME = "lastname";
  public static final String AUTH_HEADER = "Authorization";
  public static final String[] PUBLIC_URLS = { "/api/v1/auth/user/login", "/api/v1/auth/user/register",
      "/api/v1/auth/user/password/**", "/api/v1/auth/user/image/**", "/api/v1/auth/user/confirm/**",
      "/api/v1/auth/user/reflesh" };
  public static final String PROTOCOL = "smtps";
  public static final String USERNAME = "mtm.appinfo@gmail.com";
  public static final String PASSWORD = "nrtlxuglvcgjuigt";
  public static final String FROM = "mtm.appinfo@gmail.com";
  public static final String CC = "";
  public static final String GMAIL_HOST = "smtp.gmail.com";
  public static final String SMTP_HOST = "mail.smtp.host";
  public static final String SMTP_AUTH = "mail.smtp.auth";
  public static final String SMTP_PORT = "mail.smtp.port";
  public static final int DEFOULT_PORT = 465;
  public static final String SMTP_TLS_ENABLED = "mail.smtp.starttls.enable";
  public static final String SMTP_TLS_REQUIRED = "mail.smtp.starttls.required";

}
