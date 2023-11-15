package com.alex.futurity.notificationservice.scheduler;

import com.alex.futurity.notificationservice.utils.DateUtils;
import com.alex.futurity.notificationservice.scheduler.job.NotificationJob;
import com.alex.futurity.notificationservice.scheduler.model.ScheduleRequest;
import com.alex.futurity.notificationservice.utils.TaskConvertor;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.quartz.*;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.alex.futurity.notificationservice.utils.JobExtractor.extractId;

@Slf4j
@Service
public class ScheduleService {
    private final Scheduler scheduler;

    public ScheduleService(SchedulerFactoryBean schedulerFactory) {
        this.scheduler = schedulerFactory.getScheduler();
    }

    @SneakyThrows
    public void unscheduleTask(@NonNull Long taskId) {
        TriggerKey key = buildTriggerKey(taskId);

        log.info("Unscheduling task with id={}", key.getName());
        scheduler.unscheduleJob(key);
        log.info("Task with id={} has been unscheduled", key.getName());
    }

    @SneakyThrows
    public void scheduleTask(@NonNull ScheduleRequest scheduleRequest) {
        Trigger trigger = buildTrigger(scheduleRequest);
        JobDetail jobDetail = buildJobDetail(scheduleRequest);

        scheduleJob(trigger, jobDetail);
    }

    @SneakyThrows
    public void rescheduleTask(@NonNull ScheduleRequest scheduleRequest) {
        TriggerKey triggerKey = buildTriggerKey(scheduleRequest.getTaskId());
        Trigger trigger = buildTrigger(scheduleRequest);

        if (scheduler.checkExists(triggerKey)) {
            rescheduleJob(triggerKey, trigger);
        } else {
            scheduleJob(trigger, buildJobDetail(scheduleRequest));
        }
    }

    private void rescheduleJob(TriggerKey triggerKey, Trigger trigger) throws SchedulerException {
        log.info("Rescheduling job with {} id for date {}", triggerKey.getName(), trigger.getStartTime());
        scheduler.rescheduleJob(triggerKey, trigger);
        log.info("Job with {} id has been rescheduled", triggerKey.getName());
    }

    private void scheduleJob(Trigger trigger, JobDetail jobDetail) throws SchedulerException {
        log.info("Scheduling job with {} id for date {}", extractId(jobDetail), trigger.getStartTime());
        scheduler.scheduleJob(jobDetail, trigger);
        log.info("Job with {} id has been scheduled", extractId(jobDetail));
    }

    private static Trigger buildTrigger(ScheduleRequest scheduleRequest) {
        Date startAt = DateUtils.toDate(scheduleRequest.getTimeToSchedule());

        return TriggerBuilder.newTrigger()
                .withIdentity(buildIdentity(scheduleRequest.getTaskId()))
                .startAt(startAt)
                .build();
    }

    private static JobDetail buildJobDetail(ScheduleRequest scheduleRequest) {
        return JobBuilder.newJob(NotificationJob.class)
                .withDescription("Schedule notification")
                .usingJobData(TaskConvertor.toJobDataMap(scheduleRequest))
                .storeDurably()
                .build();
    }

    private static TriggerKey buildTriggerKey(@NotNull Long taskId) {
        return TriggerKey.triggerKey(buildIdentity(taskId));
    }

    private static String buildIdentity(Long key) {
        return Long.toString(key);
    }
}
