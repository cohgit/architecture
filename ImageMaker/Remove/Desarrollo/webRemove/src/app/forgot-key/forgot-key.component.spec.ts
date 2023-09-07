import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ForgotKeyComponent } from './forgot-key.component';

describe('ForgotKeyComponent', () => {
  let component: ForgotKeyComponent;
  let fixture: ComponentFixture<ForgotKeyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ForgotKeyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ForgotKeyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
