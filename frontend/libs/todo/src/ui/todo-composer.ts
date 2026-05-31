import { Component, ChangeDetectionStrategy, signal, output, input, computed } from '@angular/core';
import { Button, Textarea } from '@shared';

@Component({
  selector: 'todo-composer',
  imports: [Button, Textarea],
  changeDetection: ChangeDetectionStrategy.OnPush,
  styles: ':host {display: contents}',
  template: `
    <ui-button
      class="self-end"
      (click)="composerVisible.set(!composerVisible())"
      [disabled]="creating()"
    >
      {{ addButtonLabel() }}
    </ui-button>
    @if (composerVisible()) {
      <div class="flex flex-col gap-y-2">
        <ui-textarea
          placeholder="Describe your tasks and until when you'll finish them…"
          [(value)]="text"
          (keydown.meta.enter)="create()"
        />
        <ui-button class="self-end" (click)="create()">Create</ui-button>
      </div>
    }
  `,
})
export class TodoComposer {
  readonly creating = input.required<boolean>();
  readonly composedTodo = output<string>();

  protected readonly composerVisible = signal(false);
  protected text = signal('');

  protected readonly addButtonLabel = computed(() =>
    this.creating() ? 'Creating Todos…' : 'Add Items',
  );

  create() {
    this.composedTodo.emit(this.text());
    this.composerVisible.set(false);
    this.text.set('');
  }
}
