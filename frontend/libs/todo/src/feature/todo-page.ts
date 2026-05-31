import { Component, ChangeDetectionStrategy, inject } from '@angular/core';
import { Button } from '@shared';
import { TodoStore } from '@todo';
import { TodoItem } from '../ui/todo-item';
import { TodoComposer } from 'libs/todo/src/ui/todo-composer';

@Component({
  selector: 'todo-page',
  imports: [Button, TodoItem, TodoComposer],
  changeDetection: ChangeDetectionStrategy.OnPush,
  template: `
    <h1 class="font-bold text-xl">Dotodo</h1>
    <section class="flex flex-col mt-2 gap-y-4">
      <todo-composer [creating]="creatingTodos()" (composedTodo)="addComposedTodo($event)" />

      @for (item of todos(); track item.id) {
        <todo-item
          [item]="item"
          (delete)="deleteItem(item.id)"
          (complete)="completeItem(item.id, $event)"
        />
      }
    </section>
  `,
})
export class TodoPage {
  private readonly todoStore = inject(TodoStore);

  protected readonly todos = this.todoStore.todos;
  protected readonly creatingTodos = this.todoStore.creating;

  protected deleteItem(id: string) {
    this.todoStore.deleteItem(id).subscribe();
  }

  protected completeItem(id: string, completed: boolean) {
    this.todoStore.completeItem(id, completed).subscribe();
  }

  protected addComposedTodo(text: string) {
    this.todoStore.createFromCompose(text).subscribe();
  }
}
