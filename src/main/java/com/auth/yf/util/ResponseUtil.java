package com.auth.yf.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

import com.auth.yf.domain.HttpResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseUtil {

  public ResponseEntity<HttpResponse> createHttpResponse(HttpStatus status, String reason, String message) {
    HttpResponse response = new HttpResponse(Timestamp.valueOf(LocalDateTime.now(ZoneId.of("Asia/Tokyo"))),
        status.value(), status, reason, message);
    return new ResponseEntity<HttpResponse>(response, status);
  }
}
