import { computed, inject, Injectable } from '@angular/core';
import { TodoApi } from 'libs/todo/src/data-access/todo-api';
import { tap } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class TodoStore {
  private readonly api = inject(TodoApi);
  private readonly todoResource = this.api.getTodos();

  readonly todos = computed(() => this.todoResource.value() ?? []);

  completeItem(id: string, completed: boolean) {
    return this.api.completeTodo(id, completed).pipe(tap(() => this.todoResource.reload()));
  }

  deleteItem(id: string) {
    return this.api.deleteItem(id).pipe(tap(() => this.todoResource.reload()));
  }
}
