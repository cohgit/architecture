import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ClientBillComponent } from './client-bill.component';

describe('ClientBillComponent', () => {
  let component: ClientBillComponent;
  let fixture: ComponentFixture<ClientBillComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ClientBillComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ClientBillComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
