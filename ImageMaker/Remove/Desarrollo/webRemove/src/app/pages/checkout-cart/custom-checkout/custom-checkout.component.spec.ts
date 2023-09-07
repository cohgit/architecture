import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomCheckoutComponent } from './custom-checkout.component';

describe('CustomCheckoutComponent', () => {
  let component: CustomCheckoutComponent;
  let fixture: ComponentFixture<CustomCheckoutComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CustomCheckoutComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CustomCheckoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
