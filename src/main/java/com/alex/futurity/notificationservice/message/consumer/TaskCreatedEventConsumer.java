package com.alex.futurity.notificationservice.message.consumer;

import com.alex.futurity.notificationservice.message.model.CreationTaskEvent;
import com.alex.futurity.notificationservice.scheduler.ScheduleService;
import com.alex.futurity.notificationservice.scheduler.model.ScheduleRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("taskCreationConsumer")
public class TaskCreatedEventConsumer extends TaskEventConsumer<CreationTaskEvent> {
    public TaskCreatedEventConsumer(ScheduleService scheduleService) {
        super(scheduleService);
    }

    @Override
    protected void process(CreationTaskEvent body) {
        log.info("Got creation event with deadline '{}' for task={} and user={}",
                body.getDeadline(), body.getId(), body.getUserId());

        ScheduleRequest request = ScheduleRequest.builder()
                .taskId(body.getId())
                .userId(body.getUserId())
                .timeToSchedule(body.getDeadline())
                .build();

        scheduleService.scheduleTask(request);
    }
}
