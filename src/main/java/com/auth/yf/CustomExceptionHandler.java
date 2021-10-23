package com.auth.yf;

import com.auth.yf.consts.Messages;
import com.auth.yf.domain.HttpResponse;
import com.auth.yf.domain.exception.RefleshTokenExpiredException;
import com.auth.yf.domain.exception.UserAlreadyExistException;
import com.auth.yf.util.ResponseUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

  @Autowired
  private ResponseUtil responseUtil;

  @ExceptionHandler(UserAlreadyExistException.class)
  public ResponseEntity<HttpResponse> handleUserAlreadyExistException(UserAlreadyExistException e) {
    return responseUtil.createHttpResponse(HttpStatus.BAD_REQUEST, Messages.USER_ALREADY_EXISTS.name(),
        Messages.USER_ALREADY_EXISTS.getMessage());
  }

  @ExceptionHandler(RefleshTokenExpiredException.class)
  public ResponseEntity<HttpResponse> handleRefleshTokenExpiredException(RefleshTokenExpiredException e) {
    return responseUtil.createHttpResponse(HttpStatus.FORBIDDEN, Messages.REFLESH_TOKEN_EXPIRED.name(),
        Messages.REFLESH_TOKEN_EXPIRED.getMessage());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<HttpResponse> handleAllException(Exception e) {
    return responseUtil.createHttpResponse(HttpStatus.BAD_REQUEST, Messages.UNEXPECTED.name(), e.getMessage());
  }
}
