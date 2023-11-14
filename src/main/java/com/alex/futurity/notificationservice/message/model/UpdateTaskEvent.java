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

    public boolean hasDeadline() {
        return Objects.nonNull(deadline);
    }
}
