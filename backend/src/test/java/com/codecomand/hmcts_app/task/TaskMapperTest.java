package com.codecomand.hmcts_app.task;

import com.codecomand.hmcts_app.task.request.TaskRequest;
import com.codecomand.hmcts_app.task.response.TaskResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TaskMapperTest {

    private TaskMapper taskMapper;
    private TaskRequest taskRequest;
    private Task task;

    @BeforeEach
    void setUp() {
        taskMapper = new TaskMapper();
        
        LocalDateTime dueDateTime = LocalDateTime.now().plusDays(1);
        LocalDateTime createdAt = LocalDateTime.now();

        taskRequest = TaskRequest.builder()
                .title("Test Task")
                .description("Test Description")
                .status(TaskStatus.PENDING)
                .dueDateTime(dueDateTime)
                .build();

        task = Task.builder()
                .id("test-id-123")
                .title("Test Task")
                .description("Test Description")
                .status(TaskStatus.PENDING)
                .dueDateTime(dueDateTime)
                .createdAt(createdAt)
                .build();
    }

    @Test
    void toTask_ShouldMapTaskRequestToTask_WhenAllFieldsArePresent() {
        // When
        Task result = taskMapper.toTask(taskRequest);

        // Then
        assertNotNull(result);
        assertEquals("Test Task", result.getTitle());
        assertEquals("Test Description", result.getDescription());
        assertEquals(TaskStatus.PENDING, result.getStatus());
        assertEquals(taskRequest.getDueDateTime(), result.getDueDateTime());
    }

    @Test
    void toTask_ShouldMapTaskRequestToTask_WhenDescriptionIsNull() {
        // Given
        TaskRequest requestWithoutDescription = TaskRequest.builder()
                .title("Test Task")
                .status(TaskStatus.PENDING)
                .dueDateTime(LocalDateTime.now().plusDays(1))
                .build();

        // When
        Task result = taskMapper.toTask(requestWithoutDescription);

        // Then
        assertNotNull(result);
        assertEquals("Test Task", result.getTitle());
        assertNull(result.getDescription());
        assertEquals(TaskStatus.PENDING, result.getStatus());
    }

    @Test
    void toTaskResponse_ShouldMapTaskToTaskResponse_WhenAllFieldsArePresent() {
        // When
        TaskResponse result = taskMapper.toTaskResponse(task);

        // Then
        assertNotNull(result);
        assertEquals("test-id-123", result.getId());
        assertEquals("Test Task", result.getTitle());
        assertEquals("Test Description", result.getDescription());
        assertEquals(TaskStatus.PENDING, result.getStatus());
        assertEquals(task.getDueDateTime(), result.getDueDateTime());
        assertEquals(task.getCreatedAt(), result.getCreatedAt());
    }

    @Test
    void toTaskResponse_ShouldMapTaskToTaskResponse_WhenDescriptionIsNull() {
        // Given
        Task taskWithoutDescription = Task.builder()
                .id("test-id-456")
                .title("Test Task")
                .status(TaskStatus.PENDING)
                .dueDateTime(LocalDateTime.now().plusDays(1))
                .createdAt(LocalDateTime.now())
                .build();

        // When
        TaskResponse result = taskMapper.toTaskResponse(taskWithoutDescription);

        // Then
        assertNotNull(result);
        assertEquals("test-id-456", result.getId());
        assertEquals("Test Task", result.getTitle());
        assertNull(result.getDescription());
        assertEquals(TaskStatus.PENDING, result.getStatus());
    }

    @Test
    void toTask_ShouldMapAllTaskStatuses() {
        // Given & When & Then
        for (TaskStatus status : TaskStatus.values()) {
            TaskRequest request = TaskRequest.builder()
                    .title("Test Task")
                    .status(status)
                    .dueDateTime(LocalDateTime.now().plusDays(1))
                    .build();

            Task result = taskMapper.toTask(request);
            assertEquals(status, result.getStatus());
        }
    }

    @Test
    void toTaskResponse_ShouldMapAllTaskStatuses() {
        // Given & When & Then
        for (TaskStatus status : TaskStatus.values()) {
            Task task = Task.builder()
                    .id("test-id")
                    .title("Test Task")
                    .status(status)
                    .dueDateTime(LocalDateTime.now().plusDays(1))
                    .createdAt(LocalDateTime.now())
                    .build();

            TaskResponse result = taskMapper.toTaskResponse(task);
            assertEquals(status, result.getStatus());
        }
    }
}

