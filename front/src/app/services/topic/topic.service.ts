import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ITopic} from "../../core/models/ITopic";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment";
import {IUser} from "../../core/models/IUser";

@Injectable({
  providedIn: 'root'
})
export class TopicService {

  private apiUrl: string = environment.baseUrl + "/topics";

  constructor(
    private http: HttpClient
  ) {
  }


  findAll(): Observable<ITopic[]> {
    return this.http.get<ITopic[]>(this.apiUrl);
  }

  save(topic: ITopic): Observable<ITopic> {
    return this.http.post<ITopic>(this.apiUrl, topic);
  }

  subscribe(id: number): Observable<IUser> {
    return this.http.put<IUser>(`${this.apiUrl}/${id}/subscribe`, id.toString());
  }

  unsubscribe(id: number): Observable<IUser> {
    return this.http.delete<IUser>(`${this.apiUrl}/${id}/unsubscribe`);
  }

}
