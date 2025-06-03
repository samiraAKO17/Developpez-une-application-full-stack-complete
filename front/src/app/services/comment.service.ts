// src/app/core/services/comment.service.ts
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Comment } from 'src/app/models/comment.model';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({ providedIn: 'root' })
export class CommentService {
  private apiUrl = `${environment.apiUrl}/comments`;

  constructor(private http: HttpClient) {}

  addComment(payload: Partial<Comment>): Observable<Comment> {
    return this.http.post<Comment>(this.apiUrl, payload);
  }
}
