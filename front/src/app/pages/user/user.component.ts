import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthenticationService} from "../../services/authentication/authentication.service";
import {Router} from "@angular/router";
import {Subscription} from "rxjs";
import {IUser} from "../../core/models/IUser";
import {UserService} from "../../services/user/user.service";
import {TopicService} from "../../services/topic/topic.service";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit, OnDestroy {

  errorMessages: Array<string> = [];
  user: IUser = {
    email: '',
    id: 0,
    username: '',
    subscriptions: []
  };
  subscriptions: Subscription[] = [];

  constructor(
    private router: Router,
    private authenticationService: AuthenticationService,
    private userService: UserService,
    private topicService: TopicService,
  ) {
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => {
      subscription.unsubscribe();
    });
  }

  ngOnInit(): void {
    this.subscriptions.push(this.authenticationService.getCurrentUser().subscribe({
      next: (data) => {
        this.user = data;
      },
      error: (err) => {
        console.error(err);
        this.errorMessages.push(err.error.message);
      }
    }));
  }

  logout(): void {
    localStorage.clear();
    this.router.navigate(['/']);
  }

  save(): void {
    if (this.user) {
      this.subscriptions.push(this.userService.update(this.user).subscribe({
        next: (data) => {
          this.user = data;
          this.logout();
        },
        error: (err) => {
          console.error(err);
          this.errorMessages.push(err.error.message);
        }
      }));
    }
  }

  unsubscribe(topicId: number): void {
    this.subscriptions.push(
      this.topicService.unsubscribe(topicId).subscribe({
        next: (data) => {
          this.user = data;
        },
        error: (err) => {
          console.error(err);
          this.errorMessages.push(err.error.message);
        }
      }));

  }
}
