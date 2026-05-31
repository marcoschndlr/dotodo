import { Component, ChangeDetectionStrategy } from '@angular/core';

@Component({
  selector: 'todo-page',
  imports: [],
  changeDetection: ChangeDetectionStrategy.OnPush,
  template: `<h1 class="font-bold text-xl">Dotodo</h1>`,
})
export class TodoPage {}
