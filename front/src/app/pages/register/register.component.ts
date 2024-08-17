import { Component, OnInit } from '@angular/core';
import {IRegistrationRequest} from "../../core/models/IRegistrationRequest";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/authentication/authentication.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  registerUser : IRegistrationRequest = {
    email: '',
    password: '',
    username: ''
  };

  errorMessages: Array<string> = [];

  constructor(
    private router: Router,
    private authService : AuthenticationService
  ) { }

  ngOnInit(): void {
  }

  register(): void {
    this.errorMessages = [];
    this.authService.register(this.registerUser).subscribe(
      {
        next : async (data) =>{
          localStorage.setItem('token',data.token as string );
         await this.router.navigate(['/articles']);
        },
        error: (err) => {
          console.log(err);
          this.errorMessages = err.error.validationErrors;
        }
      }
    )
  }

  protected readonly console = console;
}
