import {Component, OnInit} from '@angular/core';
import {ArticleService} from "../../services/article/article.service";
import {IArticle} from "../../core/models/IArticle";
import {Router} from "@angular/router";

@Component({
  selector: 'app-article',
  templateUrl: './article.component.html',
  styleUrls: ['./article.component.scss']
})
export class ArticleComponent implements OnInit {

  articleList: IArticle[] = [];
  errorMessages: Array<string> = [];

  constructor(
    private router: Router,
    private articleService: ArticleService,
  ) {
  }

  ngOnInit(): void {
    this.articleService.findAll().subscribe(
      {
        next: (data) => {
          this.articleList = data;
        },
        error: (error) => {
          this.errorMessages = error.error.message;
        }
      }
    )
  }

  async show(articleId: number | undefined) {
    if (articleId != null) {
      await this.router.navigate(['/articles/', articleId]);
    }
  }

  sortArticlesByDate(): void {
    this.articleList = this.articleList.sort((a: IArticle, b: IArticle) =>
      b.created_at!.localeCompare(a.created_at!)
    );
  }

}
