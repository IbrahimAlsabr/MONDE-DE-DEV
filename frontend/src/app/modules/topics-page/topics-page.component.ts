import { Component, OnInit, inject, signal } from '@angular/core';
import { TopicService } from '../../core/services/topic.service';
import { Topic } from '../../core/models/topic.model';
import { SubscriptionBoxComponent } from '../subscription-box/subscription-box.component';

@Component({
	selector: 'app-topics-page',
	imports: [SubscriptionBoxComponent],
	templateUrl: './topics-page.component.html',
	styleUrl: './topics-page.component.scss',
})
export class TopicsPageComponent implements OnInit {
	private topicService = inject(TopicService);

	topics = signal<Topic[]>([]);
	loading = signal(true);
	error = signal<string | null>(null);
	loadingTopicId = signal<number | null>(null);

	ngOnInit(): void {
		this.loadTopics();
	}

	loadTopics(): void {
		this.loading.set(true);
		this.error.set(null);
		this.topicService.list().subscribe({
			next: (topics) => {
				this.topics.set(topics);
				this.loading.set(false);
			},
			error: () => {
				this.error.set('Erreur lors du chargement des thèmes.');
				this.loading.set(false);
			},
		});
	}

	onSubscribe(topicId: number): void {
		this.loadingTopicId.set(topicId);
		this.topicService.subscribe(topicId).subscribe({
			next: () => {
				this.topics.update((list) =>
					list.map((t) => (t.id === topicId ? { ...t, subscribed: true } : t))
				);
				this.loadingTopicId.set(null);
			},
			error: () => {
				this.loadingTopicId.set(null);
			},
		});
	}
}
