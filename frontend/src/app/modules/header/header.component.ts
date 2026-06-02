import { Component, HostListener, inject, signal } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';

@Component({
	selector: 'app-header',
	imports: [RouterLink],
	templateUrl: './header.component.html',
	styleUrl: './header.component.scss'
})
export class HeaderComponent {
	private router = inject(Router);
	private authService = inject(AuthService);

	drawerOpen = signal(false);

	toggleDrawer() {
		this.drawerOpen.update(v => !v);
	}

	closeDrawer() {
		this.drawerOpen.set(false);
	}

	logout() {
		this.authService.logout();
		this.router.navigate(['/login']);
	}
}
