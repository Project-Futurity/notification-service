package com.alex.futurity.notificationservice.utils;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.quartz.JobDetail;
import org.quartz.Trigger;

@UtilityClass
public class JobExtractor {
    public static String extractId(@NonNull JobDetail jobDetail) {
        return jobDetail.getKey().getName();
    }

    public static String extractId(@NonNull Trigger trigger) {
        return trigger.getJobKey().getName();
    }
}
