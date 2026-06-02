import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../config/api.config';
import { Post, PostCreateRequest } from '../models/post.model';

@Injectable({ providedIn: 'root' })
export class PostService {
	private http = inject(HttpClient);
	private readonly base = `${API_BASE_URL}/api/v1`;

	getFeed(sort: 'asc' | 'desc' = 'desc'): Observable<Post[]> {
		return this.http.get<Post[]>(`${this.base}/feed`, { params: { sort } });
	}

	getById(id: number): Observable<Post> {
		return this.http.get<Post>(`${this.base}/posts/${id}`);
	}

	create(payload: PostCreateRequest): Observable<Post> {
		return this.http.post<Post>(`${this.base}/posts`, payload);
	}
}
