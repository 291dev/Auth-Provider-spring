package com.auth.yf.util;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import lombok.Getter;
import lombok.Setter;

@Component
@RequestScope
@Setter
@Getter
public class RequestContext {

  private String userName;
  private String email;
  private String tel;
}
