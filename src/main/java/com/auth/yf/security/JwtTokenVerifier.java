package com.auth.yf.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "com.auth.yf")
@Getter
@Setter
public class JwtTokenVerifier {

  private String issuer;
  private String secret;

  @Bean
  public JWTVerifier jwtVerifier() {
    Algorithm algorithm = Algorithm.HMAC512(secret);
    return JWT.require(algorithm).withIssuer(issuer).build();
  }
}
