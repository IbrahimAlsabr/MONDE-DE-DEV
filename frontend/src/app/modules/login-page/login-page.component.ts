import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { LoginRequest } from '../../core/services/auth.types';
import { AuthService } from '../../core/services/auth.service';

@Component({
	selector: 'app-login-page',
	imports: [ReactiveFormsModule, RouterLink],
	templateUrl: './login-page.component.html',
	styleUrl: './login-page.component.scss'
})
export class LoginPageComponent {
	private router = inject(Router);
	submitting = false;

	private fb = inject(FormBuilder);
	private authService = inject(AuthService);
	
	form = this.fb.group({
		identifier: ['', [Validators.required]],
		password: ['', [Validators.required]],
	});

	submit() {
		if (this.form.invalid) {
			this.form.markAllAsTouched();
			return;
		}

		this.authService.login(this.form.value as LoginRequest).subscribe({
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
