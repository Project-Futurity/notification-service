package com.alex.futurity.notificationservice.notification;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class NotificationDeadlinePublisher {
    private final StreamBridge streamBridge;

    private static final String BINDER_NAME = "notificationPublisher";

    public void publish(@NonNull Notification notification) {
        log.info("Publishing notification for userId={} and taskId={}", notification.getUserId(), notification.getUserId());
        streamBridge.send(BINDER_NAME, notification);
        log.info("Notification has been published for userId={} and taskId={}", notification.getUserId(), notification.getUserId());
    }
}
