package com.codecomand.hmcts_app.task.request;

import com.codecomand.hmcts_app.task.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Task creation request model")
public class TaskRequest {

    @NotBlank(message = "Title is required")
    @Schema(description = "Task title", example = "Complete project documentation", required = true)
    private String title;

    @Schema(description = "Task description", example = "Write comprehensive documentation for the project")
    private String description;

    @NotNull(message = "Status is required")
    @Schema(description = "Task status", example = "PENDING", required = true)
    private TaskStatus status;

    @NotNull(message = "Due date/time is required")
    @Schema(description = "Due date and time", example = "2024-12-31T12:00:00", required = true)
    private LocalDateTime dueDateTime;

}


