// src/app/core/services/topic.service.ts
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Topic } from 'src/app/models/topic.model';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({ providedIn: 'root' })
export class TopicService {
  private apiUrl = `${environment.apiUrl}/topics`;
  private userTopicsUrl = `${environment.apiUrl}/user-topics`;


  constructor(private http: HttpClient) {}

  createTopic(topic: Partial<Topic>): Observable<Topic> {
    return this.http.post<Topic>(this.apiUrl, topic);
  }

  subscribeToTopic(topicId: number): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/${topicId}/subscribe`, {});
  }

  unsubscribeFromTopic(topicId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${topicId}/unsubscribe`, {});
  }

  getAllTopics(): Observable<Topic[]> {
    return this.http.get<Topic[]>(this.apiUrl);
  }

  getTopicById(id: number): Observable<Topic> {
  return this.http.get<Topic>(`${this.apiUrl}/${id}`);
  }

  getUserTopics(): Observable<Topic[]> {
    return this.http.get<Topic[]>(this.userTopicsUrl);
  }
}
