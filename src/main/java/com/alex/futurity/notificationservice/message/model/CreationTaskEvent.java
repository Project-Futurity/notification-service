package com.alex.futurity.notificationservice.message.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
public class CreationTaskEvent extends TaskEvent {
    @NonNull
    private ZonedDateTime deadline;
}
