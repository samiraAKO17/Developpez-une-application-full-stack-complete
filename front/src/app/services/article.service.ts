import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Article } from 'src/app/models/article.model';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({ providedIn: 'root' })
export class ArticleService {
  private apiUrl = `${environment.apiUrl}/articles`;
  private feedUrl = `${environment.apiUrl}/feed`;

  constructor(private http: HttpClient) {}

  createArticle(article: Partial<Article>): Observable<Article> {
    return this.http.post<Article>(this.apiUrl, article);
  }

  getAllArticles(): Observable<Article[]> {
    return this.http.get<Article[]>(this.apiUrl);
  }


  getArticleById(id: number): Observable<Article> {
    return this.http.get<Article>(`${this.apiUrl}/${id}`);
  }

  getFeed(): Observable<Article[]> {
    return this.http.get<Article[]>(this.feedUrl);
  }

}
