import { Component, ChangeDetectionStrategy } from '@angular/core';

@Component({
  selector: 'ui-button',
  imports: [],
  changeDetection: ChangeDetectionStrategy.OnPush,
  template: `
    <button
      class="cursor-pointer border border-gray-950/15 px-3 py-2 rounded-2xl hover:bg-gray-50 transition"
      style="corner-shape: squircle"
    >
      <ng-content />
    </button>
  `,
})
export class Button {}
