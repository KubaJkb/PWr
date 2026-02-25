import { Component } from '@angular/core';

@Component({
  selector: 'app-footer',
  imports: [],
  template: `
    <footer class="footer">
      <div class="container">
        &copy; 2026 - WebStore Angular Client
      </div>
    </footer>
  `,
  styles: [`
    .footer { border-top: 1px solid #dee2e6; padding: 1rem; margin-top: auto; color: #6c757d; text-align: center; }
  `]
})
export class FooterComponent {}