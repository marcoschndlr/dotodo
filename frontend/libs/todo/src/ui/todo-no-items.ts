import { Component, ChangeDetectionStrategy } from '@angular/core';
import { DotLottieComponent } from 'ngx-lottie/dotlottie-web';

@Component({
  selector: 'todo-no-items',
  imports: [DotLottieComponent],
  changeDetection: ChangeDetectionStrategy.OnPush,
  template: `
    <div class="text-center text-2xl text-gray-600">
      <ng-dotlottie [containerStyles]="{ height: '100px' }" src="/complete.lottie" autoplay />
      <p>You're all done!</p>
      <p>What's up next?</p>
    </div>
  `,
})
export class TodoNoItems {}
