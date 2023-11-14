package com.alex.futurity.notificationservice;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@UtilityClass
public class DateUtils {
    private static final ZoneId UTC_ZONE = ZoneId.of("UTC");

    public static ZonedDateTime now() {
        return ZonedDateTime.now(UTC_ZONE);
    }


    public Date toDate(@NonNull ZonedDateTime zonedDateTime) {
        return Date.from(zonedDateTime.toInstant());
    }
}
