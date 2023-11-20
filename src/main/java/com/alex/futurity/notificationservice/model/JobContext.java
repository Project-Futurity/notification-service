package com.alex.futurity.notificationservice.model;

import com.alex.futurity.notificationservice.utils.DateContext;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class JobContext extends TaskInfo {
    private final DateContext.DatePart datePart;
}
