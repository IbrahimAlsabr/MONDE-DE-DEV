import { Component, OnInit, inject, signal } from '@angular/core';
import { DatePipe } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { PostService } from '../../core/services/post.service';
import { Post } from '../../core/models/post.model';

@Component({
	selector: 'app-articles-page',
	imports: [DatePipe, RouterLink],
	templateUrl: './articles-page.component.html',
	styleUrl: './articles-page.component.scss',
})
export class ArticlesPageComponent implements OnInit {
	private postService = inject(PostService);
	private router = inject(Router);

	posts = signal<Post[]>([]);
	sort = signal<'asc' | 'desc'>('desc');
	loading = signal(true);
	error = signal<string | null>(null);

	ngOnInit(): void {
		this.loadFeed();
	}

	loadFeed(): void {
		this.loading.set(true);
		this.error.set(null);
		this.postService.getFeed(this.sort()).subscribe({
			next: (posts) => {
				this.posts.set(posts);
				this.loading.set(false);
			},
			error: () => {
				this.error.set("Erreur lors du chargement du fil d'actualité.");
				this.loading.set(false);
			},
		});
	}

	toggleSort(): void {
		this.sort.update((s) => (s === 'desc' ? 'asc' : 'desc'));
		this.loadFeed();
	}

	navigateToArticle(id: number): void {
		this.router.navigate(['/home/articles', id]);
	}
}
