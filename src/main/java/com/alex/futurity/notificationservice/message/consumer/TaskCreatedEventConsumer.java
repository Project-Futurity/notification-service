package com.alex.futurity.notificationservice.message.consumer;

import com.alex.futurity.notificationservice.message.model.CreationTaskEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("taskCreationConsumer")
public class TaskCreatedEventConsumer extends TaskEventConsumer<CreationTaskEvent> {
    @Override
    protected void process(CreationTaskEvent body) {
        log.info("Got creation event with deadline: {}", body.getDeadline());
    }
}
