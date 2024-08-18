import {Component, OnDestroy, OnInit} from '@angular/core';
import {IRegistrationRequest} from "../../core/models/IRegistrationRequest";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/authentication/authentication.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit, OnDestroy {

  registerUser: IRegistrationRequest = {
    email: '',
    password: '',
    username: ''
  };

  errorMessages: Array<string> = [];

  subscription!: Subscription;

  constructor(
    private router: Router,
    private authService: AuthenticationService
  ) {
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  ngOnInit(): void {
  }

  register(): void {
    this.errorMessages = [];
    this.subscription = this.authService.register(this.registerUser).subscribe(
      {
        next: (data) => {
          localStorage.setItem('token', data.token as string);
          this.router.navigate(['/articles']);
        },
        error: (err) => {
          console.log(err);
          this.errorMessages = err.error.validationErrors;
        }
      }
    )
  }

}
