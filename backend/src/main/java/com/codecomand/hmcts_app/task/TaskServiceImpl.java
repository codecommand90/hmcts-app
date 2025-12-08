package com.codecomand.hmcts_app.task;

import com.codecomand.hmcts_app.exception.BusinessException;
import com.codecomand.hmcts_app.exception.ErrorCode;
import com.codecomand.hmcts_app.task.request.TaskRequest;
import com.codecomand.hmcts_app.task.response.TaskResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService{

    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;

    @Override
    public TaskResponse createTask(TaskRequest taskRequest) {

        try{
            Task task = taskMapper.toTask(taskRequest);
            return taskMapper.toTaskResponse(taskRepository.save(task));

        }catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage());
            throw new BusinessException(ErrorCode.TASK_CREATION_FAILED);
        }

    }

}
