package com.codecomand.hmcts_app.task;

import com.codecomand.hmcts_app.task.request.TaskRequest;
import com.codecomand.hmcts_app.task.response.TaskResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private TaskRequest taskRequest;
    private TaskResponse taskResponse;

    @BeforeEach
    void setUp() {
        LocalDateTime dueDateTime = LocalDateTime.now().plusDays(1);

        taskRequest = TaskRequest.builder()
                .title("Test Task")
                .description("Test Description")
                .status(TaskStatus.PENDING)
                .dueDateTime(dueDateTime)
                .build();

        taskResponse = TaskResponse.builder()
                .id("test-id-123")
                .title("Test Task")
                .description("Test Description")
                .status(TaskStatus.PENDING)
                .dueDateTime(dueDateTime)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void createTask_ShouldReturnCreatedStatus_WhenRequestIsValid() {
        // Given
        when(taskService.createTask(any(TaskRequest.class))).thenReturn(taskResponse);

        // When
        ResponseEntity<TaskResponse> response = taskController.creatTask(taskRequest);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("test-id-123", response.getBody().getId());
        assertEquals("Test Task", response.getBody().getTitle());
        assertEquals("Test Description", response.getBody().getDescription());
        assertEquals(TaskStatus.PENDING, response.getBody().getStatus());
        
        verify(taskService).createTask(taskRequest);
    }

    @Test
    void createTask_ShouldReturnTaskResponse_WhenDescriptionIsNull() {
        // Given
        TaskRequest requestWithoutDescription = TaskRequest.builder()
                .title("Test Task")
                .status(TaskStatus.PENDING)
                .dueDateTime(LocalDateTime.now().plusDays(1))
                .build();

        TaskResponse responseWithoutDescription = TaskResponse.builder()
                .id("test-id-456")
                .title("Test Task")
                .status(TaskStatus.PENDING)
                .dueDateTime(LocalDateTime.now().plusDays(1))
                .createdAt(LocalDateTime.now())
                .build();

        when(taskService.createTask(any(TaskRequest.class))).thenReturn(responseWithoutDescription);

        // When
        ResponseEntity<TaskResponse> response = taskController.creatTask(requestWithoutDescription);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("test-id-456", response.getBody().getId());
        assertEquals("Test Task", response.getBody().getTitle());
        assertNull(response.getBody().getDescription());
    }
}

