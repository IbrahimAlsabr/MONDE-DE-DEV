import { Routes } from '@angular/router';
import { AuthPageComponent } from './modules/auth-page/auth-page.component';
import { LoginPageComponent } from './modules/login-page/login-page.component';
import { SignupPageComponent } from './modules/signup-page/signup-page.component';
import { HomePageComponent } from './modules/home-page/home-page.component';
import { ArticlesPageComponent } from './modules/articles-page/articles-page.component';
import { ArticleDetailsComponent } from './modules/article-details/article-details.component';
import { CreateArticleComponent } from './modules/create-article/create-article.component';
import { TopicsPageComponent } from './modules/topics-page/topics-page.component';
import { ProfilePageComponent } from './modules/profile-page/profile-page.component';
import { NotFoundComponent } from './modules/not-found/not-found.component';
import { authGuard } from './core/guards/auth.guard';

export const routes: Routes = [
	{ path: '', component: AuthPageComponent },
	{ path: 'login', component: LoginPageComponent },
	{ path: 'signup', component: SignupPageComponent },
	{
		path: 'home',
		component: HomePageComponent,
		canActivate: [authGuard],
		children: [
			{ path: '', redirectTo: 'articles', pathMatch: 'full' },
			{ path: 'articles', component: ArticlesPageComponent },
			{ path: 'articles/create', component: CreateArticleComponent },
			{ path: 'articles/:id', component: ArticleDetailsComponent },
			{ path: 'themes', component: TopicsPageComponent },
			{ path: 'profile', component: ProfilePageComponent },
		],
	},
	{ path: '**', component: NotFoundComponent },
];
