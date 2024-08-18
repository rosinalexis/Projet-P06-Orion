import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {CommentService} from "../../services/comment/comment.service";
import {IArticle} from "../../core/models/IArticle";
import {IComment} from "../../core/models/IComment";
import {ArticleService} from "../../services/article/article.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-show-article',
  templateUrl: './show-article.component.html',
  styleUrls: ['./show-article.component.scss']
})
export class ShowArticleComponent implements OnInit, OnDestroy {

  errorMessages: Array<string> = [];

  article: IArticle | undefined;
  comment: IComment = {
    article_id: 0,
    content: ''
  };

  subscriptions: Subscription[] = [];

  constructor(
    private route: ActivatedRoute,
    private commentService: CommentService,
    private articleService: ArticleService,
  ) {
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => {
      subscription.unsubscribe();
    });
  }


  ngOnInit(): void {
    const id = +this.route.snapshot.params['id'];
    this.errorMessages = [];
    this.subscriptions.push(this.articleService.findById(id).subscribe({
        next: (data) => {
          this.article = data;
        },
        error: (error) => {
          console.error(error);
          this.errorMessages.push(error.error.message);
        }
      })
    );
  }

  addComment(): void {
    this.comment.article_id = +this.route.snapshot.params['id'];

    this.subscriptions.push(this.commentService.save(this.comment).subscribe({
          next: () => {
            window.location.reload()
          },
          error: (err) => {
            console.error(err);
            this.errorMessages.push(err.error.message);
          }
        }
      )
    );
  }
}
