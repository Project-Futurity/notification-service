package com.alex.futurity.notificationservice.message.consumer;

import com.alex.futurity.notificationservice.message.model.UpdateTaskEvent;
import com.alex.futurity.notificationservice.scheduler.ScheduleService;
import com.alex.futurity.notificationservice.scheduler.model.ScheduleRequest;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Delegate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Slf4j
@Component("taskUpdateConsumer")
public class TaskUpdateEventConsumer extends TaskEventConsumer<UpdateTaskEvent> {
    private final List<ConditionCfg> conditions = List.of(
            ConditionCfg.of(UpdateTaskEvent::hasDeadline, this::processDeadlineUpdate),
            ConditionCfg.of(UpdateTaskEvent::isDeadlineRemoved, this::processDeadlineRemove),
            ConditionCfg.of(UpdateTaskEvent::getCompleted, this::processTaskCompletion)
    );

    public TaskUpdateEventConsumer(ScheduleService scheduleService) {
        super(scheduleService);
    }

    @Override
    protected void process(UpdateTaskEvent body) {
        conditions.stream()
                .filter(cfg -> cfg.test(body))
                .findFirst()
                .ifPresent(cfg -> cfg.accept(body));
    }

    private void processTaskCompletion(UpdateTaskEvent event) {
        log.info("Got completion task event for task={}", event.getId());
        scheduleService.unscheduleTask(event.getId());
    }

    private void processDeadlineRemove(UpdateTaskEvent event) {
        log.info("Got remove deadline event for task={}", event.getId());
        scheduleService.unscheduleTask(event.getId());
    }

    private void processDeadlineUpdate(UpdateTaskEvent event) {
        log.info("Got update deadline event for task={}, deadline='{}'", event.getId(), event.getDeadline());
        ScheduleRequest request = ScheduleRequest.builder()
                .userId(event.getUserId())
                .taskId(event.getId())
                .timeToSchedule(event.getDeadline())
                .build();

        scheduleService.rescheduleTask(request);
    }

    @Value(staticConstructor = "of")
    private static class ConditionCfg {
        @NonNull
        @Delegate
        Predicate<UpdateTaskEvent> predicate;

        @NonNull
        @Delegate
        Consumer<UpdateTaskEvent> consumer;
    }
}
