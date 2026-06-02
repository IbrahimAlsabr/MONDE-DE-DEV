import { Component, OnInit, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { PostService } from '../../core/services/post.service';
import { TopicService } from '../../core/services/topic.service';
import { Topic } from '../../core/models/topic.model';

@Component({
	selector: 'app-create-article',
	imports: [FormsModule],
	templateUrl: './create-article.component.html',
	styleUrl: './create-article.component.scss',
})
export class CreateArticleComponent implements OnInit {
	private postService = inject(PostService);
	private topicService = inject(TopicService);
	private router = inject(Router);

	topics = signal<Topic[]>([]);
	topicId: number | null = null;
	title = '';
	content = '';
	submitting = signal(false);
	error = signal<string | null>(null);

	ngOnInit(): void {
		this.topicService.list().subscribe({
			next: (topics) => this.topics.set(topics),
		});
	}

	goBack(): void {
		this.router.navigate(['/home/articles']);
	}

	isValid(): boolean {
		return (
			this.topicId !== null &&
			this.title.trim().length > 0 &&
			this.content.trim().length > 0
		);
	}

	submit(): void {
		if (!this.isValid() || this.submitting()) return;

		this.submitting.set(true);
		this.error.set(null);

		this.postService
			.create({
				topicId: this.topicId!,
				title: this.title.trim(),
				content: this.content.trim(),
			})
			.subscribe({
				next: (post) => {
					this.router.navigate(['/home/articles', post.id]);
				},
				error: () => {
					this.error.set("Erreur lors de la création de l'article. Veuillez réessayer.");
					this.submitting.set(false);
				},
			});
	}
}
