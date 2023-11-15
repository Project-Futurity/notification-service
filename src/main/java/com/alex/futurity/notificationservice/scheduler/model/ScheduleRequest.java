package com.alex.futurity.notificationservice.scheduler.model;

import com.alex.futurity.notificationservice.model.TaskInfo;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;
import java.util.Map;

@SuperBuilder
public class ScheduleRequest extends TaskInfo {
}
