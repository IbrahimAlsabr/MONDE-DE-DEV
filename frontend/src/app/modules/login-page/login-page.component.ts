import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { LoginRequest } from '../../core/services/auth.types';
import { AuthService } from '../../core/services/auth.service';

@Component({
	selector: 'app-login-page',
	imports: [ReactiveFormsModule, RouterLink],
	templateUrl: './login-page.component.html',
	styleUrl: './login-page.component.scss',
})
export class LoginPageComponent {
	private router = inject(Router);
	private fb = inject(FormBuilder);
	private authService = inject(AuthService);

	submitting = signal(false);
	serverError = signal<string | null>(null);

	form = this.fb.group({
		identifier: ['', [Validators.required]],
		password: ['', [
			Validators.required,
			Validators.minLength(8),
			Validators.pattern(/^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^A-Za-z0-9]).*$/),
			Validators.maxLength(50),
		]],
	});

	get identifier() { return this.form.get('identifier')!; }
	get password() { return this.form.get('password')!; }

	submit() {
		this.serverError.set(null);

		if (this.form.invalid) {
			this.form.markAllAsTouched();
			return;
		}

		this.submitting.set(true);

		this.authService.login(this.form.value as LoginRequest).subscribe({
			next: (response) => {
				this.authService.saveSession(response);
				this.router.navigate(['/home']);
			},
			error: (error) => {
				this.submitting.set(false);
				const status = error?.status;
				if (status === 401 || status === 403) {
					this.serverError.set('E-mail/nom d\'utilisateur ou mot de passe incorrect.');
				} else if (status === 0) {
					this.serverError.set('Impossible de contacter le serveur. Vérifiez votre connexion.');
				} else {
					this.serverError.set(error?.error?.message ?? 'Une erreur est survenue. Veuillez réessayer.');
				}
			},
		});
	}
}
