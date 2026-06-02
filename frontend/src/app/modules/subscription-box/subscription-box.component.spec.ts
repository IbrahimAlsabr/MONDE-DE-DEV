import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubscriptionBoxComponent } from './subscription-box.component';

describe('SubscriptionBoxComponent', () => {
  let component: SubscriptionBoxComponent;
  let fixture: ComponentFixture<SubscriptionBoxComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SubscriptionBoxComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SubscriptionBoxComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
