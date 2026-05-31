import { Component, ChangeDetectionStrategy, input, computed } from '@angular/core';

const variants = {
  default: 'border-gray-950/15 text-black bg-white',
  danger: 'border-red-600/20 text-red-600 not-disabled:hover:bg-red-600/10 bg-white',
};
type Type = keyof typeof variants;

@Component({
  selector: 'ui-button',
  imports: [],
  changeDetection: ChangeDetectionStrategy.OnPush,
  template: `
    <button
      class="text-sm cursor-pointer border px-3 py-2 rounded-2xl not-disabled:hover:bg-gray-50 transition disabled:opacity-70 disabled:cursor-default"
      [class]="variantClasses()"
      style="corner-shape: squircle"
      [disabled]="disabled()"
    >
      <ng-content />
    </button>
  `,
})
export class Button {
  readonly disabled = input(false);
  readonly variant = input<Type>('default');

  protected variantClasses = computed(() => variants[this.variant()]);
}
