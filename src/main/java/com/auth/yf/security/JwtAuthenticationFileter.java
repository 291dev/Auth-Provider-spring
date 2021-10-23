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
public class JwtAuthenticationFileter extends OncePerRequestFilter {

  @Autowired
  private JWTVerifier jwtVerifier;

  @Autowired
  private JwtVerifyUtil jwtVerifyUtil;

  @Autowired
  private RequestContext requestContext;

  private String issuer;
  private String aud;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String value = request.getHeader(Consts.AUTH_HEADER);
    if (StringUtils.isEmpty(value) || !value.startsWith(Consts.TOKEN_HEADER)) {
      log.warn("Invalid token header");
      filterChain.doFilter(request, response);
      return;
    }
    String token = value.substring(Consts.TOKEN_HEADER.length());
    String userName = null;
    try {
      userName = jwtVerifyUtil.getSubject(token);
      jwtVerifyUtil.verifyIsAccessToken(token);
      jwtVerifyUtil.verifyAud(token);
      jwtVerifyUtil.verifyExpired(token);
      jwtVerifyUtil.verifyIssuer(token);
      requestContext.setUserName(userName);
      Authentication authentication = getAuthentication(userName, jwtVerifyUtil.getAuthoritiesFromToken(token),
          request);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    } catch (Exception e) {
      e.printStackTrace();
      log.warn(e.getMessage());
      SecurityContextHolder.clearContext();
    }
    filterChain.doFilter(request, response);
  }

  private Authentication getAuthentication(String userName, List<GrantedAuthority> authorities,
      HttpServletRequest request) {
    UsernamePasswordAuthenticationToken userPasswordAuthToken = new UsernamePasswordAuthenticationToken(userName, null,
        authorities);
    userPasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    return userPasswordAuthToken;
  }

}
