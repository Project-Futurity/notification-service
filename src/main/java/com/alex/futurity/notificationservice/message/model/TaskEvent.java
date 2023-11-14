package com.alex.futurity.notificationservice.message.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class TaskEvent {
    @NonNull
    private Long id;
}
