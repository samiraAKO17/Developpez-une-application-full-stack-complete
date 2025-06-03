import { Injectable } from '@angular/core';
import { JwtResponse } from '../pages/auth/interfaces/jwt-response.inteface';

@Injectable({ providedIn: 'root' })
export class SessionService {
  private readonly key = 'auth';

  getUser(): JwtResponse | null {
    const data = sessionStorage.getItem(this.key);
    return data ? JSON.parse(data) as JwtResponse : null;
  }

  getToken(): string | null {
    return this.getUser()?.token ?? null;
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  logout(): void {
    sessionStorage.removeItem(this.key);
  }
}
