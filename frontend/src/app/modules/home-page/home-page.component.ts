import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from '../header/header.component';

@Component({
  selector: 'app-home-page',
  imports: [HeaderComponent, RouterOutlet],
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.scss',
})
export class HomePageComponent {}
