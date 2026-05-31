import { Component, ChangeDetectionStrategy, model, input } from '@angular/core';

@Component({
  selector: 'ui-textarea',
  imports: [],
  changeDetection: ChangeDetectionStrategy.OnPush,
  styles: ':host {display: contents}',
  template: `
    <textarea
      class="border border-gray-950/15 p-2 rounded-2xl placeholder:text-sm"
      style="corner-shape: squircle"
      [placeholder]="placeholder()"
      [value]="value()"
      (input)="onInput($event)"
    ></textarea>
  `,
})
export class Textarea {
  readonly placeholder = input('');
  readonly value = model<string>('');

  onInput(event: Event) {
    const value = (event.target as HTMLTextAreaElement).value;
    this.value.set(value);
  }
}
