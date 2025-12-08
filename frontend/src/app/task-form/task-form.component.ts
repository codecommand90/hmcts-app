import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { TaskService } from '../services/task.service';
import { TaskStatus, TaskRequest, TaskResponse } from '../models/task.model';

@Component({
  selector: 'app-task-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './task-form.component.html',
  styleUrls: ['./task-form.component.css']
})
export class TaskFormComponent {
  taskForm: FormGroup;
  taskStatuses = Object.values(TaskStatus);
  successMessage: string = '';
  createdTask: TaskResponse | null = null;
  errorMessage: string = '';
  isSubmitting: boolean = false;

  constructor(
    private fb: FormBuilder,
    private taskService: TaskService
  ) {
    this.taskForm = this.fb.group({
      title: ['', [Validators.required, Validators.maxLength(200)]],
      description: ['', [Validators.maxLength(1000)]],
      status: ['', Validators.required],
      dueDateTime: ['', Validators.required]
    });
  }

  get title() {
    return this.taskForm.get('title');
  }

  get description() {
    return this.taskForm.get('description');
  }

  get status() {
    return this.taskForm.get('status');
  }

  get dueDateTime() {
    return this.taskForm.get('dueDateTime');
  }

  onSubmit(): void {
    if (this.taskForm.valid) {
      this.isSubmitting = true;
      this.errorMessage = '';
      this.successMessage = '';
      this.createdTask = null;

      const taskRequest: TaskRequest = {
        title: this.taskForm.value.title,
        description: this.taskForm.value.description || undefined,
        status: this.taskForm.value.status,
        dueDateTime: this.formatDateTimeForApi(this.taskForm.value.dueDateTime)
      };

      this.taskService.createTask(taskRequest).subscribe({
        next: (response: TaskResponse) => {
          this.successMessage = 'Task created successfully!';
          this.createdTask = response;
          this.taskForm.reset();
          this.isSubmitting = false;
        },
        error: (error: Error) => {
          this.errorMessage = error.message || 'Failed to create task. Please try again.';
          this.isSubmitting = false;
        }
      });
    } else {
      this.markFormGroupTouched(this.taskForm);
    }
  }

  private formatDateTimeForApi(dateTimeString: string): string {
    // Convert local datetime to ISO format for API
    const date = new Date(dateTimeString);
    return date.toISOString();
  }

  private markFormGroupTouched(formGroup: FormGroup): void {
    Object.keys(formGroup.controls).forEach(key => {
      const control = formGroup.get(key);
      control?.markAsTouched();
    });
  }

  formatDate(dateString: string): string {
    return new Date(dateString).toLocaleString();
  }

  getStatusClass(status: TaskStatus): string {
    return `status-${status.toLowerCase()}`;
  }
}

