package com.alex.futurity.notificationservice.utils;

import com.alex.futurity.notificationservice.model.TaskInfo;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.quartz.JobDataMap;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;

@UtilityClass
public class TaskConvertor {
    private static final String TASK_KEY = "taskKey";
    private static final String USER_KEY = "userKey";
    private static final String DEADLINE_KEY = "deadlineKey";

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    public static <T extends TaskInfo> JobDataMap toJobDataMap(@NonNull T info) {
        Map<String, String> map = Map.of(
                TASK_KEY, Long.toString(info.getTaskId()),
                USER_KEY, Long.toString(info.getUserId()),
                DEADLINE_KEY, formatDate(info.getTimeToSchedule())
        );

        return new JobDataMap(map);
    }

    public static TaskInfo toTask(@NonNull JobDataMap map) {
        String taskId = Objects.requireNonNull(map.getString(TASK_KEY), "task id must be present");
        String userId = Objects.requireNonNull(map.getString(USER_KEY), "user id must be present");
        String deadline = Objects.requireNonNull(map.getString(DEADLINE_KEY), "deadline must be present");

        return TaskInfo.builder()
                .taskId(Long.parseLong(taskId))
                .userId(Long.parseLong(userId))
                .timeToSchedule(parseDate(deadline))
                .build();
    }

    private static String formatDate(ZonedDateTime dateTime) {
        return FORMATTER.format(dateTime);
    }

    private static ZonedDateTime parseDate(String date) {
        return ZonedDateTime.parse(date, FORMATTER);
    }
}
