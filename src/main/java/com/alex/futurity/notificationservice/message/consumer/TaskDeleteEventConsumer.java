package com.alex.futurity.notificationservice.message.consumer;

import com.alex.futurity.notificationservice.message.model.DeleteTaskEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("taskDeleteConsumer")
public class TaskDeleteEventConsumer extends TaskEventConsumer<DeleteTaskEvent> {
    @Override
    protected void process(DeleteTaskEvent event) {
        log.info("Got delete event with deadline: {}", event.getId());
    }
}
