import { Component, ChangeDetectionStrategy, signal, output } from '@angular/core';
import { Button, Textarea } from '@shared';

@Component({
  selector: 'todo-composer',
  imports: [Button, Textarea],
  changeDetection: ChangeDetectionStrategy.OnPush,
  styles: ':host {display: contents}',
  template: `
    <ui-button class="self-end" (click)="composerVisible.set(!composerVisible())">
      Add Item
    </ui-button>
    @if (composerVisible()) {
      <div class="flex flex-col gap-y-2">
        <ui-textarea [(value)]="text" />
        <ui-button class="self-end" (click)="composedTodo.emit(text())">Create</ui-button>
      </div>
    }
  `,
})
export class TodoComposer {
  protected readonly composerVisible = signal(false);
  text = signal('');

  composedTodo = output<string>();
}
