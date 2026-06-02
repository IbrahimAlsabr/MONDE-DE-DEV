import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../config/api.config';
import { Profile, ProfileUpdateRequest } from '../models/profile.model';

@Injectable({ providedIn: 'root' })
export class UserService {
	private http = inject(HttpClient);
	private readonly base = `${API_BASE_URL}/api/v1`;

	getProfile(): Observable<Profile> {
		return this.http.get<Profile>(`${this.base}/me`);
	}

	updateProfile(payload: ProfileUpdateRequest): Observable<Profile> {
		return this.http.put<Profile>(`${this.base}/me`, payload);
	}
}
