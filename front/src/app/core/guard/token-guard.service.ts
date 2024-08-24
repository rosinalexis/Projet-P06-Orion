import {Injectable, OnDestroy} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from "@angular/router";
import {Observable, Subscription} from 'rxjs';
import {JwtHelperService} from "@auth0/angular-jwt";
import {AuthenticationService} from "../../services/authentication/authentication.service";

@Injectable({
  providedIn: 'root'
})
export class TokenGuardService implements CanActivate, OnDestroy {

  private jwtHelperService: JwtHelperService;

  private subscription: Subscription | undefined;

  constructor(
    private router: Router,
    private authService: AuthenticationService,
  ) {
    this.jwtHelperService = new JwtHelperService();
  }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {

    const token: string | null = localStorage.getItem('token');

    if (!token) {
      this.router.navigate(['/']);
      return false;
    }

    if (token) {
      const isTokenExpired = this.jwtHelperService.isTokenExpired(token);

      if (isTokenExpired) {
        this.router.navigate(['/login']);
        return false;
      }

      this.subscription = this.authService.getCurrentUser().subscribe({
        next: () => {
          return true;
        },
        error: () => {
          this.router.navigate(['/login']);
          return false;
        }
      })
    }

    return true;
  }
}
