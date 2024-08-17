import { Component, OnInit } from '@angular/core';
import {IArticle} from "../../core/models/IArticle";
import {Router} from "@angular/router";
import {ArticleService} from "../../services/article/article.service";
import {TopicService} from "../../services/topic/topic.service";
import {ITopic} from "../../core/models/ITopic";

@Component({
  selector: 'app-new-article',
  templateUrl: './new-article.component.html',
  styleUrls: ['./new-article.component.scss']
})
export class NewArticleComponent implements OnInit {
  errorMessages: Array<string> = [];
  article : IArticle = {
    title: '',
    content: '',
    topic_id :0
  };

  topicList : Array<ITopic> = [];

  constructor(
    private router: Router,
    private articleService: ArticleService,
    private topicService: TopicService
  ) { }

  ngOnInit(): void {
    this.errorMessages = [];
    this.topicService.findAll().subscribe({
      next : (data) => {
       this.topicList = data;
      },
      error: (error) => {
        console.error(error);
        this.errorMessages = error.message;
      }
    })
  }

  create() {
    this.errorMessages = [];

    this.articleService.save(this.article).subscribe({
      next : async (data) =>{
        await this.router.navigate(['/articles']);
      },
      error : (err) =>{
        console.log(err);
        this.errorMessages.push(err.error.message);
      }
      }
    )
  }

}
