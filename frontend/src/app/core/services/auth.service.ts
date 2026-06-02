import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { API_BASE_URL } from '../config/api.config';
import type { AuthResponse, LoginRequest, SignupRequest } from './auth.types';

@Injectable({ providedIn: 'root' })
export class AuthService {
	private http = inject(HttpClient);
	private readonly accessTokenKey = 'accessToken';
	private readonly refreshTokenKey = 'refreshToken';

	login(payload: LoginRequest): Observable<AuthResponse> {
		return this.http.post<AuthResponse>(`${API_BASE_URL}/api/v1/auth/login`, payload);
	}

	signup(payload: SignupRequest): Observable<AuthResponse> {
		return this.http.post<AuthResponse>(`${API_BASE_URL}/api/v1/auth/signup`, payload);
	}

	saveSession(response: AuthResponse): void {
		sessionStorage.setItem(this.accessTokenKey, response.accessToken);
		sessionStorage.setItem(this.refreshTokenKey, response.refreshToken);
	}

	getAccessToken(): string | null {
		return sessionStorage.getItem(this.accessTokenKey);
	}

	isAuthenticated(): boolean {
		return !!this.getAccessToken();
	}

	logout(): void {
		sessionStorage.removeItem(this.accessTokenKey);
		sessionStorage.removeItem(this.refreshTokenKey);
	}
}

