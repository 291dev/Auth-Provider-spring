package com.auth.yf.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth.yf.consts.Consts;
import com.auth.yf.consts.Messages;
import com.auth.yf.util.DateTimeUtil;
import com.auth.yf.util.RequestContext;
import com.auth0.jwt.JWTVerifier;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ConfigurationProperties(prefix = "com.auth.yf")
@Setter
@Getter
@Component
public class JwtVerifyUtil {

  @Autowired
  private JWTVerifier jwtVerifier;

  private String issuer;
  private String aud;

  public String getSubject(String token) {
    return jwtVerifier.verify(token).getSubject();
  }

  public void verifyIsAccessToken(String token) throws Exception {
    if (!"access-token".equals(jwtVerifier.verify(token).getClaim(Consts.TOKEN_SPECIES).asString())) {
      log.warn(jwtVerifier.verify(token).getClaim(Consts.TOKEN_SPECIES).asString());
      throw new Exception(Messages.TOKEN_INVALID.getMessage());
    }
  }

  public void verifyIssuer(String token) throws Exception {
    if (!issuer.equals(jwtVerifier.verify(token).getIssuer())) {
      log.warn("incollect issure");
      throw new Exception(Messages.INCORRECT_ISSUER.getMessage());
    }
  }

  public void verifyAud(String token) throws Exception {
    if (!jwtVerifier.verify(token).getAudience().contains(aud)) {
      log.warn("incollect aud");
      throw new Exception(Messages.INCORRECT_AUD.getMessage());
    }
  }

  public void verifyExpired(String token) throws Exception {
    if (!DateTimeUtil.toOffsetJstDateTimeFromDate()
        .isBefore(DateTimeUtil.toOffsetJstDateTimeFromDate(jwtVerifier.verify(token).getExpiresAt()))) {
      log.warn("expired");
      throw new Exception(Messages.TOKEN_EXPIRED.getMessage());
    }
  }

  public List<GrantedAuthority> getAuthoritiesFromToken(String token) {
    String[] authorities = jwtVerifier.verify(token).getClaim(Consts.AUTHORITY_CLAIM).asArray(String.class);
    return Arrays.stream(authorities).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
  }

}
