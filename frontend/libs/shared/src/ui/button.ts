import { Component, ChangeDetectionStrategy, input } from '@angular/core';

@Component({
  selector: 'ui-button',
  imports: [],
  changeDetection: ChangeDetectionStrategy.OnPush,
  template: `
    <button
      class="cursor-pointer border border-gray-950/15 px-3 py-2 rounded-2xl not-disabled:hover:bg-gray-50 transition disabled:opacity-70 disabled:cursor-default"
      style="corner-shape: squircle"
      [disabled]="disabled()"
    >
      <ng-content />
    </button>
  `,
})
export class Button {
  readonly disabled = input(false);
}
