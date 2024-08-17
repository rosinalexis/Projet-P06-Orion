import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './pages/home/home.component';
import {ArticleComponent} from './pages/article/article.component';
import {ThemeComponent} from "./pages/theme/theme.component";
import {UserComponent} from "./pages/user/user.component";
import {NewArticleComponent} from "./pages/new-article/new-article.component";
import {ShowArticleComponent} from "./pages/show-article/show-article.component";
import {LoginComponent} from "./pages/login/login.component";
import {RegisterComponent} from "./pages/register/register.component";
import {NotFoundComponent} from "./pages/not-found/not-found.component";
import {TokenGuardService} from "./core/guard/token-guard.service";
import {MainPageComponent} from "./components/main-page/main-page.component";

// consider a guard combined with canLoad / canActivate route option
// to manage unauthenticated user to access private routes
const routes: Routes = [
  {
    path: '',
    component: HomeComponent
  },
  {
    path: '',
    component: MainPageComponent,
    children: [
      {
        path: 'login',
        component: LoginComponent
      },
      {
        path: 'register',
        component: RegisterComponent
      },
      {
        path: 'themes',
        component: ThemeComponent,
        canActivate: [TokenGuardService],
      },
      {
        path: 'me',
        component: UserComponent,
        canActivate: [TokenGuardService],
      },
    ]
  },
  {
    path: 'articles',
    canActivate: [TokenGuardService],
    component: MainPageComponent,
    children: [
      {
        path: '',
        component: ArticleComponent
      },
      {
        path: 'new',
        component: NewArticleComponent
      },
      {
        path: ':id',
        component: ShowArticleComponent
      }
    ]
  },
  {
    path: '**',
    component: NotFoundComponent,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {
}
