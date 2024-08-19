import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {IArticle} from "../../core/models/IArticle";
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  private apiUrl: string = environment.baseUrl + "/articles";

  constructor(
    private http: HttpClient
  ) {
  }

  findById(id: number): Observable<IArticle> {
    return this.http.get<IArticle>(`${this.apiUrl}/${id}`);
  }

  findAll(): Observable<Array<IArticle>> {
    return this.http.get<IArticle[]>(this.apiUrl);
  }

  save(article: IArticle): Observable<IArticle> {
    return this.http.post<IArticle>(this.apiUrl, article);
  }

  findSubscribedArticles(): Observable<IArticle[]> {
    return this.http.get<IArticle[]>(`${this.apiUrl}/subscriptions`);
  }
}
