import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('@todo').then((m) => m.TodoPage),
  },
];
