import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {Subscription} from 'rxjs';
import {ArticleService} from '../../services/article/article.service';
import {TopicService} from '../../services/topic/topic.service';
import {ITopic} from '../../core/models/ITopic';
import {IArticle} from "../../core/models/IArticle";

@Component({
  selector: 'app-new-article',
  templateUrl: './new-article.component.html',
  styleUrls: ['./new-article.component.scss']
})
export class NewArticleComponent implements OnInit, OnDestroy {
  articleForm!: FormGroup;
  topicList: ITopic[] = [];
  errorMessages: string[] = [];
  subscriptions: Subscription[] = [];

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private articleService: ArticleService,
    private topicService: TopicService
  ) {
  }

  ngOnInit(): void {
    this.errorMessages = [];

    this.articleForm = this.fb.group({
      topic_id: [0, [Validators.required, Validators.min(1)]],
      title: ['', Validators.required],
      content: ['', Validators.required]
    });

    this.subscriptions.push(this.topicService.findAll().subscribe({
      next: (data) => {
        this.topicList = data;
      },
      error: (err) => {
        console.error(err);
        this.errorMessages.push(err.error.message, err.error.validationErrors);
      }
    }));
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }

  create(): void {
    this.errorMessages = [];

    if (this.articleForm.invalid) {
      this.errorMessages.push('Veuillez remplir correctement tous les champs.');
      return;
    }

    const articleData = this.articleForm.value as IArticle;

    this.subscriptions.push(
      this.articleService.save(articleData).subscribe({
        next: () => {
          this.router.navigate(['/articles']);
        },
        error: (err) => {
          console.log(err);
          this.errorMessages.push(err.error.message);
        }
      }));
  }
}
