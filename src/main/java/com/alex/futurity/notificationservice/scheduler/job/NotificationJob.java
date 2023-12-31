package com.alex.futurity.notificationservice.scheduler.job;

import com.alex.futurity.notificationservice.model.JobContext;
import com.alex.futurity.notificationservice.model.TaskInfo;
import com.alex.futurity.notificationservice.notification.Notification;
import com.alex.futurity.notificationservice.notification.NotificationDeadlinePublisher;
import com.alex.futurity.notificationservice.utils.JobExtractor;
import com.alex.futurity.notificationservice.utils.TaskConvertor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Slf4j
@Component
@AllArgsConstructor
public class NotificationJob extends QuartzJobBean {
    private final NotificationDeadlinePublisher publisher;

    @Override
    protected void executeInternal(JobExecutionContext context) {
        log.info("Triggered job with id {}", JobExtractor.extractId(context.getTrigger()));
        JobContext jobContext = TaskConvertor.getContext(context.getMergedJobDataMap());

        Notification notification = Notification.builder()
                .taskId(jobContext.getTaskId())
                .userId(jobContext.getUserId())
                .deadline(jobContext.getTimeToSchedule())
                .part(jobContext.getDatePart().getLeftTime())
                .build();

        publisher.publish(notification);
    }
}
