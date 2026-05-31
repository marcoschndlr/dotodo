import { computed, inject, Injectable, signal } from '@angular/core';
import { TodoApi } from 'libs/todo/src/data-access/todo-api';
import { finalize, tap } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class TodoStore {
  private readonly api = inject(TodoApi);
  private readonly todoResource = this.api.getTodos();
  private readonly _creating = signal(false);

  readonly todos = computed(() => this.todoResource.value() ?? []);
  readonly creating = this._creating.asReadonly();

  completeItem(id: string, completed: boolean) {
    return this.api.completeTodo(id, completed).pipe(tap(() => this.todoResource.reload()));
  }

  deleteItem(id: string) {
    return this.api.deleteItem(id).pipe(tap(() => this.todoResource.reload()));
  }

  createFromCompose(text: string) {
    this._creating.set(true);
    return this.api.createFromCompose(text).pipe(
      tap(() => this.todoResource.reload()),
      finalize(() => this._creating.set(false)),
    );
  }
}
