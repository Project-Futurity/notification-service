package com.alex.futurity.notificationservice.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;

@Getter
@SuperBuilder
public class TaskInfo {
    @NonNull
    private final ZonedDateTime timeToSchedule;
    @NonNull
    private final Long taskId;
    @NonNull
    private final Long userId;
}
