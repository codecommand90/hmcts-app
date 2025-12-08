package com.codecomand.hmcts_app.task;

import com.codecomand.hmcts_app.exception.BusinessException;
import com.codecomand.hmcts_app.exception.ErrorCode;
import com.codecomand.hmcts_app.task.request.TaskRequest;
import com.codecomand.hmcts_app.task.response.TaskResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskMapper taskMapper;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private TaskRequest taskRequest;
    private Task task;
    private Task savedTask;
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

        task = Task.builder()
                .title("Test Task")
                .description("Test Description")
                .status(TaskStatus.PENDING)
                .dueDateTime(dueDateTime)
                .build();

        savedTask = Task.builder()
                .id("test-id-123")
                .title("Test Task")
                .description("Test Description")
                .status(TaskStatus.PENDING)
                .dueDateTime(dueDateTime)
                .createdAt(LocalDateTime.now())
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
    void createTask_ShouldReturnTaskResponse_WhenTaskIsCreatedSuccessfully() {
        // Given
        when(taskMapper.toTask(taskRequest)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(savedTask);
        when(taskMapper.toTaskResponse(savedTask)).thenReturn(taskResponse);

        // When
        TaskResponse result = taskService.createTask(taskRequest);

        // Then
        assertNotNull(result);
        assertEquals("test-id-123", result.getId());
        assertEquals("Test Task", result.getTitle());
        assertEquals("Test Description", result.getDescription());
        assertEquals(TaskStatus.PENDING, result.getStatus());
        
        verify(taskMapper).toTask(taskRequest);
        verify(taskRepository).save(task);
        verify(taskMapper).toTaskResponse(savedTask);
    }

    @Test
    void createTask_ShouldThrowBusinessException_WhenRepositoryThrowsException() {
        // Given
        when(taskMapper.toTask(taskRequest)).thenReturn(task);
        when(taskRepository.save(task)).thenThrow(new RuntimeException("Database error"));

        // When & Then
        BusinessException exception = assertThrows(BusinessException.class, 
            () -> taskService.createTask(taskRequest));
        
        assertEquals(ErrorCode.TASK_CREATION_FAILED, exception.getErrorCode());
        verify(taskMapper).toTask(taskRequest);
        verify(taskRepository).save(task);
        verify(taskMapper, never()).toTaskResponse(any());
    }

    @Test
    void createTask_ShouldThrowBusinessException_WhenMapperThrowsException() {
        // Given
        when(taskMapper.toTask(taskRequest)).thenThrow(new RuntimeException("Mapping error"));

        // When & Then
        BusinessException exception = assertThrows(BusinessException.class, 
            () -> taskService.createTask(taskRequest));
        
        assertEquals(ErrorCode.TASK_CREATION_FAILED, exception.getErrorCode());
        verify(taskMapper).toTask(taskRequest);
        verify(taskRepository, never()).save(any());
        verify(taskMapper, never()).toTaskResponse(any());
    }

    @Test
    void createTask_ShouldHandleTaskWithoutDescription() {
        // Given
        TaskRequest requestWithoutDescription = TaskRequest.builder()
                .title("Test Task")
                .status(TaskStatus.PENDING)
                .dueDateTime(LocalDateTime.now().plusDays(1))
                .build();

        Task taskWithoutDescription = Task.builder()
                .title("Test Task")
                .status(TaskStatus.PENDING)
                .dueDateTime(LocalDateTime.now().plusDays(1))
                .build();

        Task savedTaskWithoutDescription = Task.builder()
                .id("test-id-456")
                .title("Test Task")
                .status(TaskStatus.PENDING)
                .dueDateTime(LocalDateTime.now().plusDays(1))
                .createdAt(LocalDateTime.now())
                .build();

        TaskResponse responseWithoutDescription = TaskResponse.builder()
                .id("test-id-456")
                .title("Test Task")
                .status(TaskStatus.PENDING)
                .dueDateTime(LocalDateTime.now().plusDays(1))
                .createdAt(LocalDateTime.now())
                .build();

        when(taskMapper.toTask(requestWithoutDescription)).thenReturn(taskWithoutDescription);
        when(taskRepository.save(taskWithoutDescription)).thenReturn(savedTaskWithoutDescription);
        when(taskMapper.toTaskResponse(savedTaskWithoutDescription)).thenReturn(responseWithoutDescription);

        // When
        TaskResponse result = taskService.createTask(requestWithoutDescription);

        // Then
        assertNotNull(result);
        assertEquals("test-id-456", result.getId());
        assertNull(result.getDescription());
        verify(taskMapper).toTask(requestWithoutDescription);
        verify(taskRepository).save(taskWithoutDescription);
    }
}

