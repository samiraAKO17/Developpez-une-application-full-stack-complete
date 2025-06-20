import { Component, EventEmitter, Output, HostListener, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { Router } from '@angular/router';
import { SessionService } from 'src/app/services/session.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
  @Output() toggleSidenav = new EventEmitter<void>();
  isMobile = window.innerWidth <= 600;

  @HostListener('window:resize')
  onResize() {
    this.isMobile = window.innerWidth <= 600;
  }

  openSidenav() {
    this.toggleSidenav.emit();
  }
 @ViewChild('sidenav') sidenav!: MatSidenav;


  constructor(private session: SessionService, private router: Router) {}


  logoutAndClose() {
   // this.onLogout.emit();
    this.session.logout();
    this.router.navigate(['/']);
    this.sidenav.close();
}
}