import {Injectable, Type} from '@angular/core';
import {HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HttpInterceptorService implements HttpInterceptor {

  constructor() {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    if(!req.url.includes("/login") || !req.url.includes("/register")) {

       const token : string | null = localStorage.getItem('token');

       if (token !== undefined && token !== null) {

         const authReq = req.clone({
           headers: new HttpHeaders({
             Authorization: `Bearer ${token}`,
             'Content-Type': 'application/json',
           })
         });

         return next.handle(authReq);
       }
    }
    return next.handle(req);
  }

}
