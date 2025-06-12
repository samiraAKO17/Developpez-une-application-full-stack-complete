import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SessionService } from 'src/app/services/session.service';

@Component({
  selector: 'app-authenticated-layout',
  templateUrl: './authenticated-layout.component.html',
  styleUrls: ['./authenticated-layout.component.scss']
})
export class AuthenticatedLayoutComponent {
  constructor(private sessionService: SessionService, private router: Router) {}

  logout() {
    this.sessionService.logout(); // ou removeToken, etc.
    this.router.navigate(['/login']);
  }
}

