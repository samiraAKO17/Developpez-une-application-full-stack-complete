import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { SessionService } from 'src/app/services/session.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
 errorMessage = '';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private sessionService: SessionService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
    });
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      const loginRequest = this.loginForm.value;

      this.authService.login(loginRequest).subscribe({
        next: (response) => {

          this.router.navigate(['/feed']); // rediriger vers la page d’accueil post-login
        },
        error: (err) => {
          this.errorMessage = 'Identifiants invalides';
          console.error('Erreur de connexion', err);
        }
      });
    }
  }
}
