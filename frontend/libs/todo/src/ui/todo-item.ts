import { Component, ChangeDetectionStrategy, input, output, computed } from '@angular/core';
import type { Todo } from '../model/todo';
import { DatePipe } from '@angular/common';
import { Button } from '@shared';

const variants = {
  default: 'border-gray-950/15',
  completed: 'border-green-600/20 bg-green-600/5 text-green-600',
};

@Component({
  selector: 'todo-item',
  imports: [DatePipe, Button],
  changeDetection: ChangeDetectionStrategy.OnPush,
  template: `
    <div
      class="flex items-center justify-between border rounded-2xl px-4 py-2 gap-x-4"
      [class]="variantClasses()"
      style="corner-shape: squircle"
    >
      <div class="flex flex-col">
        <span class="font-medium" [class.line-through]="item().completed">{{ item().title }}</span>
        <div [class.line-through]="item().completed">{{ item().description }}</div>
        <div class="text-sm text-gray-400">Due: {{ item().dueDate | date }}</div>
      </div>
      <div class="flex gap-x-2">
        <ui-button (click)="complete.emit(!item().completed)">{{ this.completeLabel() }}</ui-button>
        <ui-button variant="danger" (click)="delete.emit()">Delete</ui-button>
      </div>
    </div>
  `,
})
export class TodoItem {
  readonly item = input.required<Todo>();
  readonly complete = output<boolean>();
  readonly delete = output();

  protected readonly completeLabel = computed(() =>
    this.item().completed ? 'Uncomplete' : 'Complete',
  );

  protected variantClasses = computed(
    () => variants[this.item().completed ? 'completed' : 'default'],
  );
}
