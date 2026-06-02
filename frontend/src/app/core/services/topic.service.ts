import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../config/api.config';
import { Topic } from '../models/topic.model';

@Injectable({ providedIn: 'root' })
export class TopicService {
	private http = inject(HttpClient);
	private readonly base = `${API_BASE_URL}/api/v1`;

	list(): Observable<Topic[]> {
		return this.http.get<Topic[]>(`${this.base}/topics`);
	}

	subscribe(topicId: number): Observable<void> {
		return this.http.post<void>(`${this.base}/topics/${topicId}/subscribe`, {});
	}

	unsubscribe(topicId: number): Observable<void> {
		return this.http.delete<void>(`${this.base}/subscriptions/${topicId}`);
	}
}
