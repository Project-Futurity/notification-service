package com.alex.futurity.notificationservice.utils;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@UtilityClass
public class DateUtils {
    public Date toDate(@NonNull ZonedDateTime zonedDateTime) {
        return Date.from(zonedDateTime.toInstant());
    }
}
