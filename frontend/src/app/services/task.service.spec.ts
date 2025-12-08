import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TaskService } from './task.service';
import { TaskRequest, TaskResponse, TaskStatus } from '../models/task.model';

describe('TaskService', () => {
  let service: TaskService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [TaskService]
    });
    service = TestBed.inject(TaskService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should create a task successfully', () => {
    const taskRequest: TaskRequest = {
      title: 'Test Task',
      description: 'Test Description',
      status: TaskStatus.PENDING,
      dueDateTime: '2024-12-31T12:00:00'
    };

    const mockResponse: TaskResponse = {
      id: 'test-id-123',
      title: 'Test Task',
      description: 'Test Description',
      status: TaskStatus.PENDING,
      dueDateTime: '2024-12-31T12:00:00',
      createdAt: '2024-01-01T10:00:00'
    };

    service.createTask(taskRequest).subscribe(response => {
      expect(response).toEqual(mockResponse);
      expect(response.id).toBe('test-id-123');
      expect(response.title).toBe('Test Task');
    });

    const req = httpMock.expectOne('http://localhost:8080/api/v1/tasks');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(taskRequest);
    req.flush(mockResponse);
  });

  it('should handle error when creating a task', () => {
    const taskRequest: TaskRequest = {
      title: '',
      status: TaskStatus.PENDING,
      dueDateTime: '2024-12-31T12:00:00'
    };

    const mockError = {
      message: 'Validation failed',
      fieldErrors: [
        { field: 'title', message: 'Title is required' }
      ]
    };

    service.createTask(taskRequest).subscribe({
      next: () => fail('should have failed'),
      error: (error) => {
        expect(error.message).toContain('Validation');
      }
    });

    const req = httpMock.expectOne('http://localhost:8080/api/v1/tasks');
    req.flush(mockError, { status: 400, statusText: 'Bad Request' });
  });
});

