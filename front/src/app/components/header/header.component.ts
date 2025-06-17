import { Component, EventEmitter, Output, ViewChild, HostListener } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { Router } from '@angular/router';
import { SessionService } from 'src/app/services/session.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent {
  @Output() onLogout = new EventEmitter<void>();
  @ViewChild('sidenav') sidenav!: MatSidenav;

  isMobile = window.innerWidth <= 600;

  constructor(private session: SessionService, private router: Router) {}

  @HostListener('window:resize')
  onResize() {
    this.isMobile = window.innerWidth <= 600;
  }

  logoutAndClose() {
    this.onLogout.emit();
    this.session.logout();
    this.router.navigate(['/']);
    this.sidenav.close();
  }
}
