import { Component, EventEmitter, Output, ViewChild, HostListener } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent {
  @Output() onLogout = new EventEmitter<void>();
  @ViewChild('sidenav') sidenav!: MatSidenav;

  isMobile = window.innerWidth <= 600;

  @HostListener('window:resize')
  onResize() {
    this.isMobile = window.innerWidth <= 600;
  }

  logoutAndClose() {
    this.onLogout.emit();
    this.sidenav.close();
  }
}
