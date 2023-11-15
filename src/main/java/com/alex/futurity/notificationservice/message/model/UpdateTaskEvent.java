package com.alex.futurity.notificationservice.message.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.ZonedDateTime;
import java.util.Objects;

@Data
@NoArgsConstructor
public class UpdateTaskEvent extends TaskEvent {
    private ZonedDateTime deadline;
    @NonNull
    private Boolean completed;
    @NonNull
    private Long userId;

    public boolean hasDeadline() {
        return Objects.nonNull(deadline);
    }

    public boolean isDeadlineRemoved() {
        return !hasDeadline();
    }
}
