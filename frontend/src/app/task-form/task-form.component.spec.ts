import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TaskFormComponent } from './task-form.component';
import { TaskService } from '../services/task.service';
import { TaskStatus, TaskResponse } from '../models/task.model';
import { of, throwError } from 'rxjs';

describe('TaskFormComponent', () => {
  let component: TaskFormComponent;
  let fixture: ComponentFixture<TaskFormComponent>;
  let taskService: jasmine.SpyObj<TaskService>;

  beforeEach(async () => {
    const taskServiceSpy = jasmine.createSpyObj('TaskService', ['createTask']);

    await TestBed.configureTestingModule({
      imports: [
        TaskFormComponent,
        ReactiveFormsModule,
        HttpClientTestingModule
      ],
      providers: [
        { provide: TaskService, useValue: taskServiceSpy }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(TaskFormComponent);
    component = fixture.componentInstance;
    taskService = TestBed.inject(TaskService) as jasmine.SpyObj<TaskService>;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize form with empty values', () => {
    expect(component.taskForm.get('title')?.value).toBe('');
    expect(component.taskForm.get('description')?.value).toBe('');
    expect(component.taskForm.get('status')?.value).toBe('');
    expect(component.taskForm.get('dueDateTime')?.value).toBe('');
  });

  it('should validate required fields', () => {
    const titleControl = component.taskForm.get('title');
    const statusControl = component.taskForm.get('status');
    const dueDateTimeControl = component.taskForm.get('dueDateTime');

    expect(titleControl?.valid).toBeFalsy();
    expect(statusControl?.valid).toBeFalsy();
    expect(dueDateTimeControl?.valid).toBeFalsy();

    titleControl?.setValue('Test Title');
    statusControl?.setValue(TaskStatus.PENDING);
    dueDateTimeControl?.setValue('2024-12-31T12:00');

    expect(titleControl?.valid).toBeTruthy();
    expect(statusControl?.valid).toBeTruthy();
    expect(dueDateTimeControl?.valid).toBeTruthy();
  });

  it('should submit form and create task successfully', () => {
    const mockResponse: TaskResponse = {
      id: 'test-id-123',
      title: 'Test Task',
      description: 'Test Description',
      status: TaskStatus.PENDING,
      dueDateTime: '2024-12-31T12:00:00',
      createdAt: '2024-01-01T10:00:00'
    };

    taskService.createTask.and.returnValue(of(mockResponse));

    component.taskForm.patchValue({
      title: 'Test Task',
      description: 'Test Description',
      status: TaskStatus.PENDING,
      dueDateTime: '2024-12-31T12:00'
    });

    component.onSubmit();

    expect(taskService.createTask).toHaveBeenCalled();
    expect(component.successMessage).toBe('Task created successfully!');
    expect(component.createdTask).toEqual(mockResponse);
  });

  it('should handle error on task creation', () => {
    const error = new Error('Validation failed');
    taskService.createTask.and.returnValue(throwError(() => error));

    component.taskForm.patchValue({
      title: 'Test Task',
      status: TaskStatus.PENDING,
      dueDateTime: '2024-12-31T12:00'
    });

    component.onSubmit();

    expect(component.errorMessage).toBe('Validation failed');
    expect(component.createdTask).toBeNull();
  });

  it('should not submit if form is invalid', () => {
    component.onSubmit();
    expect(taskService.createTask).not.toHaveBeenCalled();
  });
});

