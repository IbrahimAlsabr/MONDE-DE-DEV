import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
	selector: 'app-subscription-box',
	imports: [],
	templateUrl: './subscription-box.component.html',
	styleUrl: './subscription-box.component.scss',
})
export class SubscriptionBoxComponent {
	@Input({ required: true }) name!: string;
	@Input() description: string = '';
	@Input() subscribed: boolean = false;
	@Input() mode: 'topics' | 'profile' = 'topics';

	@Input() loading: boolean = false;

	@Output() subscribe = new EventEmitter<void>();
	@Output() unsubscribe = new EventEmitter<void>();
}
