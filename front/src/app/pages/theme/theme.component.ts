import {Component, OnDestroy, OnInit} from '@angular/core';
import {TopicService} from "../../services/topic/topic.service";
import {Subscription} from "rxjs";
import {ITopic} from "../../core/models/ITopic";

@Component({
  selector: 'app-theme',
  templateUrl: './theme.component.html',
  styleUrls: ['./theme.component.scss']
})
export class ThemeComponent implements OnInit, OnDestroy {

  errorMessages: Array<string> = [];
  successMessages: Array<string> = [];
  topicList: Array<ITopic> = [];

  subscription!: Subscription;

  constructor(
    private topicService: TopicService,
  ) {
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  ngOnInit(): void {
    this.errorMessages = [];

    this.subscription = this.topicService.findAll().subscribe({
      next: (data) => {
        this.topicList = data;
      },
      error: (err) => {
        console.error(err);
        this.errorMessages.push(err.error.message);
      }
    })
  }

  subscribe(id: number | undefined): void {
    if (id != null) {
      this.subscription = this.topicService.subscribe(id).subscribe({
        next: () => {
          this.successMessages.push("inscription ok.")
        },
        error: (err) => {
          console.error(err);
          this.errorMessages.push(err.error.message);
        }
      })
    }
  }
}
