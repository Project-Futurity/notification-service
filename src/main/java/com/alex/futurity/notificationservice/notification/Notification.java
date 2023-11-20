package com.alex.futurity.notificationservice.notification;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.ZonedDateTime;

@Value
@Builder
public class Notification {
    @NonNull
    Long userId;
    @NonNull
    Long taskId;
    @NonNull
    ZonedDateTime deadline;
    @NonNull
    String part;
}
