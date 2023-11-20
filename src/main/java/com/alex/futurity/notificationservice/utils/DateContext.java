package com.alex.futurity.notificationservice.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Value(staticConstructor = "of")
public class DateContext {
    @NonNull ZonedDateTime deadline;
    @NonNull DatePart datePart;

    public String getDatePart() {
        return datePart.getLeftTime();
    }

    @Getter
    @AllArgsConstructor
    public enum DatePart {
        DAY("1 day"),
        MINUTES("5 minutes");

        public static final Map<String, DatePart> CACHE = Arrays.stream(values())
                .collect(Collectors.toMap(DatePart::getLeftTime, Function.identity()));

        private final String leftTime;


        public static DatePart fromValue(@NonNull String value) {
            return CACHE.get(value);
        }

    }
}
