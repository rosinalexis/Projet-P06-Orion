import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {Subscription} from 'rxjs';
import {AuthenticationService} from '../../services/authentication/authentication.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit, OnDestroy {

  registerForm!: FormGroup;
  errorMessages: Array<string> = [];
  private subscription: Subscription | null = null;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private authService: AuthenticationService
  ) {

  }

  ngOnInit(): void {
    this.registerForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [
        Validators.required,
        Validators.minLength(8),
        this.passwordPatternValidator()
      ]]
    });
  }

  passwordPatternValidator() {
    return (control: FormControl) => {
      const value = control.value;
      if (!value) return null;
      const pattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&#])[A-Za-z\d@$!%*?&#]{8,20}$/;
      return pattern.test(value) ? null : {passwordPattern: true};
    };
  }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

  register(): void {
    if (this.registerForm.invalid) {
      return;
    }

    this.errorMessages = [];
    const registerUser = this.registerForm.value;

    this.subscription = this.authService.register(registerUser).subscribe(
      {
        next: () => {
          this.router.navigate(['/login']);
        },
        error: (err) => {
          console.error('Registration error:', err);
          this.errorMessages.push('Une erreur est survenue lors de l\'inscription.');
        }
      }
    );
  }
}
