import { Component, OnInit, inject, signal } from '@angular/core';
import { DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { PostService } from '../../core/services/post.service';
import { CommentService } from '../../core/services/comment.service';
import { Post } from '../../core/models/post.model';

@Component({
	selector: 'app-article-details',
	imports: [DatePipe, FormsModule],
	templateUrl: './article-details.component.html',
	styleUrl: './article-details.component.scss',
})
export class ArticleDetailsComponent implements OnInit {
	private postService = inject(PostService);
	private commentService = inject(CommentService);
	private route = inject(ActivatedRoute);
	private router = inject(Router);

	post = signal<Post | null>(null);
	loading = signal(true);
	error = signal<string | null>(null);
	newComment = '';
	submitting = signal(false);

	ngOnInit(): void {
		const id = Number(this.route.snapshot.paramMap.get('id'));
		this.postService.getById(id).subscribe({
			next: (post) => {
				this.post.set(post);
				this.loading.set(false);
			},
			error: () => {
				this.error.set('Article introuvable.');
				this.loading.set(false);
			},
		});
	}

	goBack(): void {
		this.router.navigate(['/home/articles']);
	}

	submitComment(): void {
		const content = this.newComment.trim();
		if (!content || !this.post() || this.submitting()) return;

		this.submitting.set(true);
		this.commentService.add(this.post()!.id, content).subscribe({
			next: (comment) => {
				this.post.update((p) =>
					p ? { ...p, comments: [...p.comments, comment] } : p
				);
				this.newComment = '';
				this.submitting.set(false);
			},
			error: () => {
				this.submitting.set(false);
			},
		});
	}
}
