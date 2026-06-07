import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink, RouterModule } from '@angular/router';
import { SignupRequest } from '../../core/services/auth.types';
import { AuthService } from '../../core/services/auth.service';

@Component({
	selector: 'app-signup-page',
	imports: [ReactiveFormsModule, RouterLink, RouterModule],
	templateUrl: './signup-page.component.html',
	styleUrl: './signup-page.component.scss',
})
export class SignupPageComponent {
	private router = inject(Router);
	private fb = inject(FormBuilder);
	private authService = inject(AuthService);

	submitting = signal(false);
	serverError = signal<string | null>(null);

	form = this.fb.group({
		username: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
		email: ['', [Validators.required, Validators.email]],
		password: ['', [
			Validators.required,
			Validators.minLength(8),
			Validators.pattern(/^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^A-Za-z0-9]).*$/),
			Validators.maxLength(50),
		]],
	});

	get username() { return this.form.get('username')!; }
	get email() { return this.form.get('email')!; }
	get password() { return this.form.get('password')!; }

	submit() {
		this.serverError.set(null);

		if (this.form.invalid) {
			this.form.markAllAsTouched();
			return;
		}

		this.submitting.set(true);

		this.authService.signup(this.form.value as SignupRequest).subscribe({
			next: (response) => {
				this.authService.saveSession(response);
				this.router.navigate(['/home']);
			},
			error: (error) => {
				this.submitting.set(false);
				const status = error?.status;
				if (status === 409) {
					this.serverError.set('Cet e-mail ou ce nom d\'utilisateur est déjà utilisé.');
				} else if (status === 0) {
					this.serverError.set('Impossible de contacter le serveur. Vérifiez votre connexion.');
				} else {
					this.serverError.set(error?.error?.message ?? 'Une erreur est survenue. Veuillez réessayer.');
				}
			},
		});
	}
}
