package com.alex.futurity.notificationservice.scheduler;

import com.alex.futurity.notificationservice.scheduler.job.NotificationJob;
import com.alex.futurity.notificationservice.scheduler.model.ScheduleRequest;
import com.alex.futurity.notificationservice.utils.DateContext;
import com.alex.futurity.notificationservice.utils.DateSplitter;
import com.alex.futurity.notificationservice.utils.DateUtils;
import com.alex.futurity.notificationservice.utils.TaskConvertor;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.data.util.Pair;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
        List<TriggerKey> keys = List.of(
                buildTriggerKey(taskId, DateContext.DatePart.DAY),
                buildTriggerKey(taskId, DateContext.DatePart.MINUTES)
        );

        log.info("Unscheduling task with id={}", taskId);
        scheduler.unscheduleJobs(keys);
        log.info("Task with id={} has been unscheduled", taskId);
    }

    public void scheduleTask(@NonNull ScheduleRequest scheduleRequest) {
        DateSplitter.splitDate(scheduleRequest.getTimeToSchedule()).stream()
                .map(context -> Pair.of(
                        buildTrigger(context, scheduleRequest.getTaskId()),
                        buildJobDetail(scheduleRequest, context))
                )
                .forEach(pair -> scheduleJob(pair.getFirst(), pair.getSecond()));
    }

    @SneakyThrows
    public void rescheduleTask(@NonNull ScheduleRequest scheduleRequest) {
        unscheduleTask(scheduleRequest.getTaskId());
        scheduleTask(scheduleRequest);
    }

    @SneakyThrows
    private void scheduleJob(Trigger trigger, JobDetail jobDetail) {
        log.info("Scheduling job with {} id for date {}", extractId(jobDetail), trigger.getStartTime());
        scheduler.scheduleJob(jobDetail, trigger);
        log.info("Job with {} id has been scheduled", extractId(jobDetail));
    }

    private static Trigger buildTrigger(DateContext context, Long taskId) {
        Date startAt = DateUtils.toDate(context.getDeadline());

        return TriggerBuilder.newTrigger()
                .withIdentity(TriggerKey.triggerKey(taskId.toString(), context.getDatePart()))
                .startAt(startAt)
                .build();
    }

    private static JobDetail buildJobDetail(ScheduleRequest scheduleRequest, DateContext context) {
        return JobBuilder.newJob(NotificationJob.class)
                .withDescription("Schedule notification")
                .usingJobData(TaskConvertor.toJobDataMap(scheduleRequest, context))
                .storeDurably()
                .build();
    }

    private static TriggerKey buildTriggerKey(Long taskId, DateContext.DatePart datePart) {
        return TriggerKey.triggerKey(taskId.toString(), datePart.getLeftTime());
    }
}
