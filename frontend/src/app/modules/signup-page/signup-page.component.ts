import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink, RouterModule } from '@angular/router';
import { SignupRequest } from '../../core/services/auth.types';
import { AuthService } from '../../core/services/auth.service';

@Component({
	selector: 'app-signup-page',
	imports: [ReactiveFormsModule, RouterLink, RouterModule],
	templateUrl: './signup-page.component.html',
	styleUrl: './signup-page.component.scss'
})
export class SignupPageComponent {
	private router = inject(Router);
	submitting = false;

	private fb = inject(FormBuilder);
	private authService = inject(AuthService);

	form = this.fb.group({
		username: ['', [Validators.required, Validators.minLength(3)]],
		email: ['', [Validators.required, Validators.email]],
		password: ['', [
			Validators.required,
			Validators.minLength(8),
			Validators.pattern(/^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^A-Za-z0-9]).*$/),
			Validators.maxLength(50),
		]],
	});

	submit() {
		if (this.form.invalid) {
			this.form.markAllAsTouched();
			return;
		}

		this.authService.signup(this.form.value as SignupRequest).subscribe({
			next: (response) => {
				console.log(response);
				this.authService.saveSession(response);
				this.router.navigate(['/home']);
			},
			error: (error) => {
				console.error(error);
			}
		});
	}
}
