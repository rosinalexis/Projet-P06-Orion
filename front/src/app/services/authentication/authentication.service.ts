import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {IUser} from "../../core/models/IUser";
import {IRegistrationRequest} from "../../core/models/IRegistrationRequest";
import {Observable} from "rxjs";
import {ILoginRequest} from "../../core/models/ILoginRequest";
import {environment} from "../../../environments/environment";
import {IToken} from "../../core/models/IToken";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private apiUrl = environment.baseUrl + "/auth";

  constructor(
    private http: HttpClient
  ) {
  }

  register(newUser: IRegistrationRequest): Observable<IToken> {
    return this.http.post<IToken>(`${this.apiUrl}/register`, newUser);
  }

  login(user: ILoginRequest): Observable<IToken> {
    return this.http.post<IToken>(`${this.apiUrl}/login`, user);
  }

  getCurrentUser(): Observable<IUser> {
    return this.http.get<IUser>(`${this.apiUrl}/me`);
  }


}
