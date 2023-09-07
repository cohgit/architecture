import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PaymentsBillsComponent } from './payments-bills.component';

describe('PaymentsBillsComponent', () => {
  let component: PaymentsBillsComponent;
  let fixture: ComponentFixture<PaymentsBillsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PaymentsBillsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PaymentsBillsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
