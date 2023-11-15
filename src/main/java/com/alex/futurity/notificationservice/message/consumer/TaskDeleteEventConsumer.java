package com.alex.futurity.notificationservice.message.consumer;

import com.alex.futurity.notificationservice.message.model.DeleteTaskEvent;
import com.alex.futurity.notificationservice.scheduler.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("taskDeleteConsumer")
public class TaskDeleteEventConsumer extends TaskEventConsumer<DeleteTaskEvent> {
    public TaskDeleteEventConsumer(ScheduleService scheduleService) {
        super(scheduleService);
    }

    @Override
    protected void process(DeleteTaskEvent event) {
        log.info("Got delete event with id: {}", event.getId());
        scheduleService.unscheduleTask(event.getId());
    }
}
