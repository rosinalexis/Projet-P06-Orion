import {Component, OnDestroy, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthenticationService} from '../../services/authentication/authentication.service';
import {Subscription} from 'rxjs';
import {ILoginRequest} from "../../core/models/ILoginRequest";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit, OnDestroy {

  loginForm!: FormGroup;
  errorMessages: Array<string> = [];
  private subscription: Subscription | null = null;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private authService: AuthenticationService
  ) {
  }

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      login: ['', [Validators.required]],
      password: ['', Validators.required]
    });
  }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

  login(): void {
    if (this.loginForm.invalid) {
      return;
    }

    this.errorMessages = [];
    const loginUser = this.loginForm.value as ILoginRequest;

    this.subscription = this.authService.login(loginUser).subscribe(
      {
        next: (data) => {
          localStorage.setItem('token', data.token as string);
          this.router.navigate(['/articles']);
        },
        error: (err) => {
          console.error('Login error:', err);
          this.errorMessages.push('Une erreur est survenue lors de la connexion.');
        }
      }
    );
  }
}
