import {NgModule} from '@angular/core';
import {MatButtonModule} from '@angular/material/button';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HomeComponent} from './pages/home/home.component';
import {LoginComponent} from './pages/login/login.component';
import {RegisterComponent} from './pages/register/register.component';
import {MenuComponent} from './components/menu/menu.component';
import {ArticleComponent} from './pages/article/article.component';
import {ThemeComponent} from './pages/theme/theme.component';
import {UserComponent} from './pages/user/user.component';
import {NewArticleComponent} from './pages/new-article/new-article.component';
import {ShowArticleComponent} from './pages/show-article/show-article.component';
import {NotFoundComponent} from './pages/not-found/not-found.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {HttpInterceptorService} from "./core/interceptor/http-interceptor.service";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NgOptimizedImage} from "@angular/common";
import {MainPageComponent} from './components/main-page/main-page.component';
import {HttpErrorInterceptorService} from "./core/interceptor/http-error-interceptor.service";
import {UnauthorizedComponent} from './pages/unauthorized/unauthorized.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    MenuComponent,
    ArticleComponent,
    ThemeComponent,
    UserComponent,
    NewArticleComponent,
    ShowArticleComponent,
    NotFoundComponent,
    MainPageComponent,
    UnauthorizedComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    HttpClientModule,
    FormsModule,
    NgOptimizedImage,
    ReactiveFormsModule,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpInterceptorService,
      multi: true,
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpErrorInterceptorService,
      multi: true,
    }
  ],
  bootstrap: [AppComponent],
})
export class AppModule {
}
