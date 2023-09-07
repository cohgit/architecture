import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalBillingComponent } from './modal-billing.component';

describe('ModalBillingComponent', () => {
  let component: ModalBillingComponent;
  let fixture: ComponentFixture<ModalBillingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ModalBillingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ModalBillingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
