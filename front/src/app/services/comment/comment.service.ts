import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {IComment} from "../../core/models/IComment";
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  private apiUrl: string = environment.baseUrl + "/comments";

  constructor(
    private http: HttpClient
  ) {
  }

  findByArticleId(id: number): Observable<IComment[]> {
    return this.http.get<IComment[]>(`${this.apiUrl}/${id}`);
  }

  save(comment: IComment): Observable<IComment> {
    return this.http.post<IComment>(this.apiUrl, comment);
  }
}
