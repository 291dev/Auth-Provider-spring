package com.auth.yf.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseTokenHeader {

  private String jwtAccessToken;
  private String jwtIdToken;
  private String refleshToken;
}
