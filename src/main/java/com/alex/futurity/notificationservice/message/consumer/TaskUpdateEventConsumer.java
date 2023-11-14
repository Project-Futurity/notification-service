package com.alex.futurity.notificationservice.message.consumer;

import com.alex.futurity.notificationservice.message.model.UpdateTaskEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("taskUpdateConsumer")
public class TaskUpdateEventConsumer extends TaskEventConsumer<UpdateTaskEvent> {
    @Override
    protected void process(UpdateTaskEvent body) {
        log.info("Got update event with deadline: {}", body.getDeadline());
        log.info("Deadline removed: {}", body.hasDeadline());
        log.info("Task is completed: {}", body.getCompleted());
    }
}
