package com.auth.yf.security;

import java.text.ParseException;
import java.util.Date;
import java.util.stream.Collectors;

import com.auth.yf.consts.Consts;
import com.auth.yf.domain.ResponseTokenHeader;
import com.auth.yf.domain.UserPrincipal;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenProvider {

  @Value("${com.auth.yf.secret}")
  private String secret;
  @Value("${com.auth.yf.issuer}")
  private String issure;
  @Value("${com.auth.yf.aud}")
  private String aud;
  @Value("${com.auth.yf.tokenExpTime}")
  private String tokenExpTime;
  @Value("${com.auth.yf.refleshTokenExpTime}")
  private String refleshTokenExpTime;

  public ResponseTokenHeader generateJwtTokens(UserPrincipal user) throws ParseException {
    String[] authorities = getAuthoritiesFromUser(user);
    Date issuedAt = new Date();

    ResponseTokenHeader res = new ResponseTokenHeader();
    res.setJwtAccessToken(generateAccessToken(user, authorities, issuedAt));
    res.setJwtIdToken(generateIdToken(user, authorities, issuedAt));
    res.setRefleshToken(generateRefleshToken(user, authorities, issuedAt));
    return res;
  }

  public ResponseTokenHeader generateWithoutRefleshTokens(UserPrincipal user) throws ParseException {
    String[] authorities = getAuthoritiesFromUser(user);
    Date issuedAt = new Date();

    ResponseTokenHeader res = new ResponseTokenHeader();
    res.setJwtAccessToken(generateAccessToken(user, authorities, issuedAt));
    res.setJwtIdToken(generateIdToken(user, authorities, issuedAt));
    return res;
  }

  private String generateAccessToken(UserPrincipal user, String[] authorities, Date issuedAt) throws ParseException {
    Date expiredAt = new Date(issuedAt.getTime() + Long.parseLong(tokenExpTime));
    String accessToken = JWT.create().withArrayClaim(Consts.AUTHORITY_CLAIM, authorities).withIssuer(issure)
        .withAudience(aud).withIssuedAt(issuedAt).withSubject(user.getUsername()).withExpiresAt(expiredAt)
        .withClaim(Consts.TOKEN_SPECIES, "access-token").sign(Algorithm.HMAC512(secret.getBytes()));
    return accessToken;
  }

  private String generateIdToken(UserPrincipal user, String[] authorities, Date issuedAt) throws ParseException {
    Date expiredAt = new Date(issuedAt.getTime() + Long.parseLong(tokenExpTime));
    String idToken = JWT.create().withIssuer(issure).withAudience(aud).withIssuedAt(issuedAt)
        .withSubject(user.getUsername()).withArrayClaim(Consts.AUTHORITY_CLAIM, authorities)
        .withClaim(Consts.TEL_CLAIM, user.getUserEntity().getTelephoneNumber())
        .withClaim(Consts.EMAIL, user.getUserEntity().getEmail())
        .withClaim(Consts.FIRSTNAME, user.getUserEntity().getFirstName())
        .withClaim(Consts.LASTNAME, user.getUserEntity().getLastName()).withExpiresAt(expiredAt)
        .sign(Algorithm.HMAC512(secret.getBytes()));

    return idToken;
  }

  private String generateRefleshToken(UserPrincipal user, String[] authorities, Date issuedAt) throws ParseException {
    Date refleshTokenExpired = new Date(issuedAt.getTime() + Long.parseLong(refleshTokenExpTime));
    String refleshToken = JWT.create().withIssuer(issure).withAudience(aud).withIssuedAt(issuedAt)
        .withSubject(user.getUsername()).withExpiresAt(refleshTokenExpired).sign(Algorithm.HMAC512(secret.getBytes()));
    return refleshToken;
  }

  private String[] getAuthoritiesFromUser(UserPrincipal user) {
    return user.getAuthorities().stream().map(v -> v.getAuthority()).collect(Collectors.toList())
        .toArray(new String[0]);
  }

}
