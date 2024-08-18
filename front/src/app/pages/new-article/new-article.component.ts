import {Component, OnDestroy, OnInit} from '@angular/core';
import {IArticle} from "../../core/models/IArticle";
import {Router} from "@angular/router";
import {ArticleService} from "../../services/article/article.service";
import {TopicService} from "../../services/topic/topic.service";
import {ITopic} from "../../core/models/ITopic";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-new-article',
  templateUrl: './new-article.component.html',
  styleUrls: ['./new-article.component.scss']
})
export class NewArticleComponent implements OnInit, OnDestroy {
  errorMessages: Array<string> = [];
  article: IArticle = {
    title: '',
    content: '',
    topic_id: 0
  };

  topicList: Array<ITopic> = [];

  subscriptions: Subscription[] = [];

  constructor(
    private router: Router,
    private articleService: ArticleService,
    private topicService: TopicService
  ) {
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => {
      subscription.unsubscribe();
    });
  }

  ngOnInit(): void {
    this.errorMessages = [];
    this.subscriptions.push(this.topicService.findAll().subscribe({
        next: (data) => {
          this.topicList = data;
        },
        error: (err) => {
          console.error(err);
          this.errorMessages = err.message;
        }
      })
    );
  }

  create(): void {
    this.errorMessages = [];

    this.subscriptions.push(this.articleService.save(this.article).subscribe({
          next: () => {
            this.router.navigate(['/articles']);
          },
          error: (err) => {
            console.log(err);
            this.errorMessages.push(err.error.message);
          }
        }
      )
    );
  }

}
