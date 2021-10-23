package com.auth.yf.util;

import java.util.Base64;

import com.auth.yf.security.TokenBodyBean;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FromTokenUtil {

  public static String getUserNameFromAccessToken(String token) {
    String body64 = token.split("\\.")[1];
    String body = Base64.getDecoder().decode(body64).toString();
    ObjectMapper m = new ObjectMapper();
    TokenBodyBean b = new TokenBodyBean();
    try {
      b = m.readValue(body, TokenBodyBean.class);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return b.getSub();
  }
}
