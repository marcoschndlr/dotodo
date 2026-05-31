import { inject, Injectable } from '@angular/core';
import { HttpClient, httpResource } from '@angular/common/http';
import type { Todo } from '../model/todo';

@Injectable({ providedIn: 'root' })
export class TodoApi {
  private readonly httpClient = inject(HttpClient);

  getTodos() {
    return httpResource<Todo[]>(() => '/api/todos');
  }

  completeTodo(id: string, completed: boolean) {
    return this.httpClient.patch<void>(`/api/todos/${id}`, {
      completed,
    });
  }

  deleteItem(id: string) {
    return this.httpClient.delete(`/api/todos/${id}`);
  }

  createFromCompose(text: string) {
    return this.httpClient.post('/api/todos', {
      text,
    });
  }
}
