import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable, tap } from 'rxjs';
import { SignupRequest } from '../pages/auth/interfaces/signup-request.interface';
import { LoginRequest } from '../pages/auth/interfaces/login-request.interface';
import { User } from 'src/app/models/user.model';
import { JwtResponse } from '../pages/auth/interfaces/jwt-response.inteface';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private apiUrl = `${environment.apiUrl}/auth`;

  constructor(private http: HttpClient) {}

  login(request: LoginRequest): Observable<JwtResponse> {
  return this.http.post<JwtResponse>(`${this.apiUrl}/login`, request).pipe(
    tap(response => {
      this.saveSession(response);
    })
  );
}

register(request: SignupRequest): Observable<JwtResponse> {
  return this.http.post<JwtResponse>(`${this.apiUrl}/register`, request).pipe(
    tap(response => {
      this.saveSession(response);
    })
  );
}

private saveSession(response: JwtResponse): void {
  sessionStorage.setItem('auth', JSON.stringify(response));
}

}
