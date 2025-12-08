package com.codecomand.hmcts_app.task;

import com.codecomand.hmcts_app.task.request.TaskRequest;
import com.codecomand.hmcts_app.task.response.TaskResponse;
import org.springframework.stereotype.Service;

@Service
public class TaskMapper {

    public Task toTask(TaskRequest request) {
        return Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus())
                .dueDateTime(request.getDueDateTime())
                .build();
    }


    public TaskResponse toTaskResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .dueDateTime(task.getDueDateTime())
                .createdAt(task.getCreatedAt())
                .build();
    }
}
