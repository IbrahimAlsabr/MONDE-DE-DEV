import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../config/api.config';
import { PostComment } from '../models/post.model';

@Injectable({ providedIn: 'root' })
export class CommentService {
	private http = inject(HttpClient);
	private readonly base = `${API_BASE_URL}/api/v1`;

	add(postId: number, content: string): Observable<PostComment> {
		return this.http.post<PostComment>(`${this.base}/posts/${postId}/comments`, { content });
	}
}
