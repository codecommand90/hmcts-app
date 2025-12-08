package com.codecomand.hmcts_app.task;

import com.codecomand.hmcts_app.task.request.TaskRequest;
import com.codecomand.hmcts_app.task.response.TaskResponse;
import org.springframework.stereotype.Service;

@Service
public interface TaskService {
    public TaskResponse createTask(TaskRequest taskRequest);
}
