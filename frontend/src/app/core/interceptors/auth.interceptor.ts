import { HttpInterceptorFn, HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, switchMap, throwError } from 'rxjs';

import { AuthService } from '../services/auth.service';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
	const authService = inject(AuthService);
	const router = inject(Router);

	const withBearer = (request: typeof req, token: string) =>
		request.clone({ setHeaders: { Authorization: `Bearer ${token}` } });

	const isAuthEndpoint = req.url.includes('/api/v1/auth/');
	const token = !isAuthEndpoint ? authService.getAccessToken() : null;
	const authReq = token ? withBearer(req, token) : req;

	return next(authReq).pipe(
		catchError((error: HttpErrorResponse) => {
			if (error.status !== 401 || req.url.includes('/auth/refresh')) {
				return throwError(() => error);
			}

			if (!authService.getRefreshToken()) {
				authService.logout();
				router.navigate(['/login']);
				return throwError(() => error);
			}

			return authService.refresh().pipe(
				switchMap(response => {
					authService.updateAccessToken(response.accessToken);
					return next(withBearer(req, response.accessToken));
				}),
				catchError(refreshError => {
					authService.logout();
					router.navigate(['/login']);
					return throwError(() => refreshError);
				})
			);
		})
	);
};
