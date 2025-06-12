import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { RegisterComponent } from './pages/auth/register/register.component';
import { LoginComponent } from './pages/auth/login/login.component';
import { FeedComponent } from './pages/feed/feed.component';
import { WelcomeComponent } from './pages/welcome/welcome.component';
import { AuthenticatedLayoutComponent } from './components/authenticated-layout/authenticated-layout.component';
import { TopicsComponent } from './pages/topics/topics-list/topics.component';
import { ArticleDetailsComponent } from './pages/articles/article-details/article-details.component';

// consider a guard combined with canLoad / canActivate route option
// to manage unauthenticated user to access private routes
const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: '', component: WelcomeComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  {
    path: '', component: AuthenticatedLayoutComponent,
    // canActivate: [AuthGuard],  // protégé par l'authentification
    children: [
      { path: 'feed', component: FeedComponent },
      { path: 'topics-list', component: TopicsComponent },
      {
        path: 'article-details/:id', component: ArticleDetailsComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule { }
