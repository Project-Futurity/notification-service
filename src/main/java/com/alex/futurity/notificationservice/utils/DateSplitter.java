package com.alex.futurity.notificationservice.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.UtilityClass;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

@UtilityClass
public class DateSplitter {
    private static final long FIVE_MINUTES_DEADLINE = 5;
    private static final long ONE_DAY_DEADLINE = 1;

    public List<DateContext> splitDate(@NonNull ZonedDateTime date) {
        if (isLaterThenOneDay(date)) {
            return List.of(oneDayContext(date), fiveMinutesContext(date));
        }

        if (isLaterThenFiveMinutes(date)) {
            return List.of(fiveMinutesContext(date));
        }

        return Collections.emptyList();
    }

    private static DateContext fiveMinutesContext(ZonedDateTime dateTime) {
        return DateContext.of(dateTime.minusMinutes(FIVE_MINUTES_DEADLINE), DateContext.DatePart.MINUTES);
    }

    private static DateContext oneDayContext(ZonedDateTime dateTime) {
        return DateContext.of(dateTime.minusDays(ONE_DAY_DEADLINE), DateContext.DatePart.DAY);
    }

    private static boolean isLaterThenOneDay(ZonedDateTime dateTime) {
        return ChronoUnit.DAYS.between(DateUtils.now(), dateTime) > 1;
    }

    private static boolean isLaterThenFiveMinutes(ZonedDateTime dateTime) {
        return ChronoUnit.MINUTES.between(DateUtils.now(), dateTime) > 5;
    }
}
