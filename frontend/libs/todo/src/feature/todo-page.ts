import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { TodoStore } from '@todo';
import { TodoItem } from '../ui/todo-item';
import { TodoComposer } from '../ui/todo-composer';
import { TodoNoItems } from '../ui/todo-no-items';

@Component({
  selector: 'todo-page',
  imports: [TodoItem, TodoComposer, TodoNoItems, TodoNoItems],
  changeDetection: ChangeDetectionStrategy.OnPush,
  template: `
    <h1 class="font-bold text-xl text-[#0063d5]">do:todo</h1>
    <section class="flex flex-col mt-2 gap-y-4">
      <todo-composer [creating]="creatingTodos()" (composedTodo)="addComposedTodo($event)" />

      @for (item of todos(); track item.id) {
        <todo-item
          [item]="item"
          (delete)="deleteItem(item.id)"
          (complete)="completeItem(item.id, $event)"
        />
      } @empty {
        <todo-no-items />
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
