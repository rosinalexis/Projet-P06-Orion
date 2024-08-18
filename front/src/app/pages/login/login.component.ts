import {Component, OnDestroy, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/authentication/authentication.service";
import {ILoginRequest} from "../../core/models/ILoginRequest";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit, OnDestroy {

  loginUser: ILoginRequest = {
    login: '',
    password: ''
  };

  errorMessages: Array<string> = [];

  subscription!: Subscription;

  constructor(
    private router: Router,
    private authService: AuthenticationService,
  ) {
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  ngOnInit(): void {
  }

  login(): void {
    this.errorMessages = [];
    this.subscription = this.authService.login(this.loginUser).subscribe(
      {
        next: (data) => {
          localStorage.setItem('token', data.token as string);
          this.router.navigate(['/articles']);
        },
        error: (err) => {
          console.log(err);
          this.errorMessages.push(err.error.message);
        }
      }
    )
  }

}
