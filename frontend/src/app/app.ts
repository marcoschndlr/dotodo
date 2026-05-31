import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  template: `
    <main class="max-w-xl mx-auto pbs-6">
      <router-outlet />
    </main>
  `,
})
export class App {}
