package com.auth.yf;

import java.text.ParseException;

import com.auth.yf.consts.Consts;
import com.auth.yf.domain.LoginReqBean;
import com.auth.yf.domain.ResponseTokenHeader;
import com.auth.yf.domain.UserInfoResBean;
import com.auth.yf.domain.UserPrincipal;
import com.auth.yf.domain.UserRegisterReqBean;
import com.auth.yf.domain.exception.RefleshTokenExpiredException;
import com.auth.yf.entity.UserEntity;
import com.auth.yf.security.JwtTokenProvider;
import com.auth.yf.security.JwtVerifyUtil;
import com.auth.yf.service.UserService;
import com.auth.yf.util.RequestContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/v1/auth/user")
@Slf4j
public class Controller {

  @Autowired
  private UserService userService;
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private JwtTokenProvider jwtTokenProvider;
  @Autowired
  private RequestContext requestContext;
  @Autowired
  private JwtVerifyUtil jwtVerifyUtil;

  @PostMapping("register")
  public String postUser(@RequestBody UserRegisterReqBean req) throws Exception {
    return userService.register(req);
  }

  @GetMapping("confirm/{uuid}")
  public String configure(@PathVariable("uuid") String uuid) throws Exception {
    userService.configure(uuid);
    return "<p>認証されたよ!<p>";
  }

  @PostMapping("login")
  public ResponseEntity<UserInfoResBean> login(@RequestBody LoginReqBean req) throws ParseException {
    log.info("start auth");
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
    log.info("authenticated");
    log.info("called service");
    UserEntity loginUser = userService.findByEmail(req.getEmail());
    UserPrincipal userPrincipal = new UserPrincipal(loginUser);
    ResponseTokenHeader resHeader = jwtTokenProvider.generateJwtTokens(userPrincipal);
    HttpHeaders headers = new HttpHeaders();
    headers.add(Consts.JWT_ACCESS_TOKEN_HEADER, resHeader.getJwtAccessToken());
    headers.add(Consts.JWT_ID_TOKEN_HEADER, resHeader.getJwtIdToken());
    headers.add(Consts.JWT_REFLESH_TOKEN_HEADER, resHeader.getRefleshToken());
    UserInfoResBean res = new UserInfoResBean();
    res.setEmail(loginUser.getEmail());
    res.setFirstname(loginUser.getFirstName());
    res.setUserId(loginUser.getUserName());
    res.setTel(loginUser.getTelephoneNumber());
    return new ResponseEntity<UserInfoResBean>(res, headers, HttpStatus.OK);
  }

  @DeleteMapping
  public boolean deleteUser() {
    log.info(requestContext.getUserName());
    userService.deleteUser(requestContext.getUserName());
    return true;
  }

  @GetMapping("reflesh")
  public ResponseEntity<Void> reflesh(@RequestHeader("jwt-reflesh-token") String refleshToken) throws Exception {
    try {
      jwtVerifyUtil.verifyExpired(refleshToken);
    } catch (Exception e) {
      throw new RefleshTokenExpiredException();
    }
    String username = jwtVerifyUtil.getSubject(refleshToken);
    jwtVerifyUtil.verifyAud(refleshToken);
    jwtVerifyUtil.verifyIssuer(refleshToken);

    UserEntity loginUser = userService.findByUserName(username);
    UserPrincipal userPrincipal = new UserPrincipal(loginUser);
    ResponseTokenHeader resHeader = jwtTokenProvider.generateWithoutRefleshTokens(userPrincipal);
    HttpHeaders headers = new HttpHeaders();
    headers.add(Consts.JWT_ACCESS_TOKEN_HEADER, resHeader.getJwtAccessToken());
    headers.add(Consts.JWT_ID_TOKEN_HEADER, resHeader.getJwtIdToken());

    return new ResponseEntity<Void>(headers, HttpStatus.OK);
  }
}
