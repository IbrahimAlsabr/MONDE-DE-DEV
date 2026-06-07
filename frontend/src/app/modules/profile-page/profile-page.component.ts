import { Component, OnInit, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { UserService } from '../../core/services/user.service';
import { TopicService } from '../../core/services/topic.service';
import { Profile } from '../../core/models/profile.model';
import { SubscriptionBoxComponent } from '../subscription-box/subscription-box.component';

@Component({
	selector: 'app-profile-page',
	imports: [ReactiveFormsModule, SubscriptionBoxComponent],
	templateUrl: './profile-page.component.html',
	styleUrl: './profile-page.component.scss',
})
export class ProfilePageComponent implements OnInit {
	private fb = inject(FormBuilder);
	private userService = inject(UserService);
	private topicService = inject(TopicService);

	profile = signal<Profile | null>(null);
	loading = signal(true);
	saving = signal(false);
	saveError = signal<string | null>(null);
	saveSuccess = signal(false);
	loadingUnsubTopicId = signal<number | null>(null);

	form = this.fb.group({
		username: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
		email: ['', [Validators.required, Validators.email]],
		password: ['', [Validators.minLength(8), Validators.pattern(/^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^A-Za-z0-9]).*$/), Validators.maxLength(50)]],
	});

	ngOnInit(): void {
		this.userService.getProfile().subscribe({
			next: (profile) => {
				this.profile.set(profile);
				this.form.patchValue({ username: profile.username, email: profile.email });
				this.loading.set(false);
			},
			error: () => this.loading.set(false),
		});
	}

	save(): void {
		if (this.form.invalid || this.saving()) {
			this.form.markAllAsTouched();
			return;
		}

		this.saving.set(true);
		this.saveError.set(null);
		this.saveSuccess.set(false);

		const { username, email, password } = this.form.value;

		this.userService.updateProfile({
			username: username!,
			email: email!,
			...(password ? { password } : {}),
		}).subscribe({
			next: (profile) => {
				this.profile.set(profile);
				this.form.patchValue({ username: profile.username, email: profile.email, password: '' });
				this.saving.set(false);
				this.saveSuccess.set(true);
				setTimeout(() => this.saveSuccess.set(false), 3000);
			},
			error: (err) => {
				this.saveError.set(err?.error?.message ?? 'Erreur lors de la mise à jour du profil.');
				this.saving.set(false);
			},
		});
	}

	onUnsubscribe(topicId: number): void {
		this.loadingUnsubTopicId.set(topicId);
		this.topicService.unsubscribe(topicId).subscribe({
			next: () => {
				this.profile.update((p) =>
					p ? { ...p, subscriptions: p.subscriptions.filter((s) => s.id !== topicId) } : p
				);
				this.loadingUnsubTopicId.set(null);
			},
			error: () => this.loadingUnsubTopicId.set(null),
		});
	}
}
