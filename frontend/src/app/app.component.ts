import { Component } from '@angular/core';
import { TaskFormComponent } from './task-form/task-form.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [TaskFormComponent],
  template: `
    <div class="container">
      <div class="card">
        <h1>Task Management Application</h1>
        <p style="color: #666; margin-bottom: 30px;">Create and manage your tasks</p>
        <app-task-form></app-task-form>
      </div>
    </div>
  `,
  styles: []
})
export class AppComponent {
  title = 'Task Management System';
}

