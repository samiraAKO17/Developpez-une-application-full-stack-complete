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
import { ArticleCreateComponent } from './pages/articles/article-create/article-create.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { AuthGuard } from './guards/auth.guard';
import { NotFoundComponent } from './components/not-found/not-found.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: '', component: WelcomeComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  {
    path: '', component: AuthenticatedLayoutComponent, canActivate: [AuthGuard],
    // canActivate: [AuthGuard],  // protégé par l'authentification
    children: [
      { path: 'feed', component: FeedComponent },
      { path: 'topics-list', component: TopicsComponent},
      {
        path: 'article-details/:id', component: ArticleDetailsComponent
      },
      {
        path: 'article-create', component: ArticleCreateComponent
      },
      {
        path: 'profile', component: ProfileComponent
      }
    ]
  },
      { path: '404', component: NotFoundComponent },
      { path: '**', redirectTo: '404' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule { }
