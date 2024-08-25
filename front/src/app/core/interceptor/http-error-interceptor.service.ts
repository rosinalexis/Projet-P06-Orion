import {Injectable} from "@angular/core";
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {catchError, Observable, throwError} from "rxjs";
import {Router} from "@angular/router";

@Injectable()
export class HttpErrorInterceptor implements HttpInterceptor {

  constructor(
    private router: Router
  ) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(
      catchError((error: HttpErrorResponse) => {
        let errorMessage = '';
        if (error.error instanceof ErrorEvent) {
          // client-side error
          errorMessage = `Error: ${error.error.message}`;
        } else { // HttpErrorResponse
          switch (error.status) {
            case 400: // BadRequest
              break;
            case 401: // Unauthorized
              localStorage.clear();
              this.router.navigateByUrl('/login');
              break;
            case 403: // Forbidden
              this.router.navigateByUrl('/unauthorized');
              break;
            case 404: // NotFound
              this.router.navigateByUrl('/notFound');
              break;
            default:
              // Internal error
              break;
          }
          errorMessage = `Error Code: ${error.status}\nMessage: ${error.error.message}`;
        }
        return throwError(errorMessage);
      })
    );
  }

}
