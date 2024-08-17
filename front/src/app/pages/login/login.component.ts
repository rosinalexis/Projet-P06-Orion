import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/authentication/authentication.service";
import {ILoginRequest} from "../../core/models/ILoginRequest";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginUser : ILoginRequest  = {
    login: '',
    password: ''
  };

  errorMessages: Array<string> = [];

  constructor(
    private router: Router,
    private authService: AuthenticationService,
  ) { }

  ngOnInit(): void {
  }

  login(): void {
    this.errorMessages = [];
    this.authService.login(this.loginUser).subscribe(
      {
        next : async (data) =>{
          localStorage.setItem('token',data.token as string );
          await this.router.navigate(['/articles']);
        },
        error: (err) => {
          console.log(err);
          this.errorMessages.push(err.error.message);
        }
      }
    )
  }

}
