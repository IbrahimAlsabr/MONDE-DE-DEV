import { Component, OnInit, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../core/services/user.service';
import { TopicService } from '../../core/services/topic.service';
import { Profile } from '../../core/models/profile.model';
import { SubscriptionBoxComponent } from '../subscription-box/subscription-box.component';

@Component({
	selector: 'app-profile-page',
	imports: [FormsModule, SubscriptionBoxComponent],
	templateUrl: './profile-page.component.html',
	styleUrl: './profile-page.component.scss',
})
export class ProfilePageComponent implements OnInit {
	private userService = inject(UserService);
	private topicService = inject(TopicService);

	profile = signal<Profile | null>(null);
	loading = signal(true);

	username = '';
	email = '';
	password = '';
	confirmPassword = '';

	saving = signal(false);
	saveError = signal<string | null>(null);
	saveSuccess = signal(false);

	loadingUnsubTopicId = signal<number | null>(null);

	ngOnInit(): void {
		this.userService.getProfile().subscribe({
			next: (profile) => {
				this.profile.set(profile);
				this.username = profile.username;
				this.email = profile.email;
				this.loading.set(false);
			},
			error: () => {
				this.loading.set(false);
			},
		});
	}

	save(): void {
		if (this.saving()) return;

		this.saving.set(true);
		this.saveError.set(null);
		this.saveSuccess.set(false);

		this.userService
			.updateProfile({
				email: this.email,
				username: this.username,
				...(this.password ? { password: this.password, confirmPassword: this.confirmPassword } : {}),
			})
			.subscribe({
				next: (profile) => {
					this.profile.set(profile);
					this.username = profile.username;
					this.email = profile.email;
					this.password = '';
					this.confirmPassword = '';
					this.saving.set(false);
					this.saveSuccess.set(true);
					setTimeout(() => this.saveSuccess.set(false), 3000);
				},
				error: (err) => {
					this.saveError.set(
						err?.error?.message ?? 'Erreur lors de la mise à jour du profil.'
					);
					this.saving.set(false);
				},
			});
	}

	onUnsubscribe(topicId: number): void {
		this.loadingUnsubTopicId.set(topicId);
		this.topicService.unsubscribe(topicId).subscribe({
			next: () => {
				this.profile.update((p) =>
					p
						? { ...p, subscriptions: p.subscriptions.filter((s) => s.id !== topicId) }
						: p
				);
				this.loadingUnsubTopicId.set(null);
			},
			error: () => {
				this.loadingUnsubTopicId.set(null);
			},
		});
	}
}
