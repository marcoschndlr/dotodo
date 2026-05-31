import { Component, ChangeDetectionStrategy, model } from '@angular/core';

@Component({
  selector: 'ui-textarea',
  imports: [],
  changeDetection: ChangeDetectionStrategy.OnPush,
  styles: ':host {display: contents}',
  template: `
    <textarea
      class="border border-gray-950/15 p-2 rounded-2xl "
      style="corner-shape: squircle"
      [value]="value()"
      (input)="onInput($event)"
    ></textarea>
  `,
})
export class Textarea {
  value = model<string>('');

  onInput(event: Event) {
    const value = (event.target as HTMLTextAreaElement).value;
    this.value.set(value);
  }
}
