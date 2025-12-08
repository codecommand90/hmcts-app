package com.codecomand.hmcts_app.task.response;

import com.codecomand.hmcts_app.task.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Task response model")
public class TaskResponse {
    @Schema(description = "Task unique identifier", example = "123e4567-e89b-12d3-a456-426614174000")
    private String id;
    
    @Schema(description = "Task title", example = "Complete project documentation")
    private String title;
    
    @Schema(description = "Task description", example = "Write comprehensive documentation for the project")
    private String description;
    
    @Schema(description = "Task status", example = "PENDING")
    private TaskStatus status;
    
    @Schema(description = "Due date and time", example = "2024-12-31T12:00:00")
    private LocalDateTime dueDateTime;
    
    @Schema(description = "Creation timestamp", example = "2024-01-01T10:00:00")
    private LocalDateTime createdAt;
}


