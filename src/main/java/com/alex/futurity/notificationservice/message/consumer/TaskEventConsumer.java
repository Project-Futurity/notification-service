package com.alex.futurity.notificationservice.message.consumer;

import com.alex.futurity.notificationservice.message.model.TaskEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;

import java.util.Optional;
import java.util.function.Consumer;

@Slf4j
public abstract class TaskEventConsumer<T extends TaskEvent> implements Consumer<Message<T>> {
    private static final String HEADER_NAME = "type";

    @Override
    public void accept(Message<T> message) {
        T payload = message.getPayload();
        log.info("Got event with '{}' routing key with task id {}", getRoutingKey(message), payload.getId());
        // validate message

        process(payload);
    }

    protected abstract void process(T body);

    private String getRoutingKey(Message<T> message) {
        return Optional.ofNullable(message.getHeaders().get(HEADER_NAME, String.class))
                .orElseThrow(() -> new IllegalStateException("Failed to receive routing key"));
    }
}
