import { Component } from '@angular/core';

@Component({
  selector: 'app-header',
  imports: [],
  template: `
    <header class="header">
      <nav>
        <span class="brand">WebStore Client</span>
      </nav>
    </header>
  `,
  styles: [`
    .header { background-color: #fff; border-bottom: 1px solid #dee2e6; padding: 1rem; margin-bottom: 1rem; }
    .brand { font-size: 1.25rem; font-weight: 500; }
  `]
})
export class HeaderComponent {}