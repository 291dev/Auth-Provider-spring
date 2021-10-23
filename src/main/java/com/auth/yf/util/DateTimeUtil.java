package com.auth.yf.util;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateTimeUtil {

  public static OffsetDateTime toOffsetJstDateTimeFromDate(Date date) {
    return OffsetDateTime.ofInstant(date.toInstant(), ZoneId.of("Asia/Tokyo"));
  }

  public static OffsetDateTime toOffsetJstDateTimeFromDate() {
    return OffsetDateTime.ofInstant(new Date().toInstant(), ZoneId.of("Asia/Tokyo"));
  }
}
