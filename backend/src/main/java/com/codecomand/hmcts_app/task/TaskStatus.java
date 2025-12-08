package com.codecomand.hmcts_app.task;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Task status enumeration")
public enum TaskStatus {
    @Schema(description = "Task is pending")
    PENDING("Pending"),
    @Schema(description = "Task is in progress")
    IN_PROGRESS("In Progress"),
    @Schema(description = "Task is completed")
    COMPLETED("Completed"),
    @Schema(description = "Task is cancelled")
    CANCELLED("Cancelled");

    private final String displayName;

    TaskStatus(String displayName){
        this.displayName = displayName;
    }

    public String getDisplayName(){
        return displayName;
    }
}
