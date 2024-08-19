import {Component, OnDestroy, OnInit} from '@angular/core';
import {ArticleService} from "../../services/article/article.service";
import {IArticle} from "../../core/models/IArticle";
import {Router} from "@angular/router";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-article',
  templateUrl: './article.component.html',
  styleUrls: ['./article.component.scss']
})
export class ArticleComponent implements OnInit, OnDestroy {

  articleList: IArticle[] = [];
  errorMessages: Array<string> = [];

  subscription!: Subscription;

  constructor(
    private router: Router,
    private articleService: ArticleService,
  ) {
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  ngOnInit(): void {
    this.subscription = this.articleService.findSubscribedArticles().subscribe(
      {
        next: (data) => {
          this.articleList = data;
        },
        error: (err) => {
          this.errorMessages = err.error.message;
        }
      }
    )
  }

  show(articleId: number): void {
    this.router.navigate(['/articles/', articleId]);

  }

  sortArticlesByDate(): void {
    this.articleList = this.articleList.sort((a: IArticle, b: IArticle) =>
      b.created_at!.localeCompare(a.created_at!)
    );
  }

}
