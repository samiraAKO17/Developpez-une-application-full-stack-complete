import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './pages/home/home.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { TokenInterceptor } from './interceptors/token.interceptor';
import { FeedComponent } from './pages/feed/feed.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AuthRoutingModule } from './pages/auth/auth-routing.module';
import { MaterialModule } from './shared/material/material.module';
import { AuthModule } from './pages/auth/auth.module';
import { WelcomeComponent } from './pages/welcome/welcome.component';
import { HeaderComponent } from './components/header/header.component';
import { AuthenticatedLayoutComponent } from './components/authenticated-layout/authenticated-layout.component';
import { MatSidenavModule } from '@angular/material/sidenav';
import { ArticleCardComponent } from './components/article-card/article-card.component';
import { TopicsComponent } from './pages/topics/topics-list/topics.component';
import { ArticleDetailsComponent } from './pages/articles/article-details/article-details.component';
import { ArticleCreateComponent } from './pages/articles/article-create/article-create.component';
import { ProfileComponent } from './pages/profile/profile.component';



@NgModule({
  declarations: [AppComponent, HomeComponent, FeedComponent, WelcomeComponent, HeaderComponent, AuthenticatedLayoutComponent, ArticleCardComponent, TopicsComponent, ArticleDetailsComponent, ArticleCreateComponent, ProfileComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    AuthRoutingModule,
    AuthModule,
    MatSidenavModule,
    HttpClientModule,


  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
