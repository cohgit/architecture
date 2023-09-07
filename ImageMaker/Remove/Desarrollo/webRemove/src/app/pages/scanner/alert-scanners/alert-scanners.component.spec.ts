import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AlertScannersComponent } from './alert-scanners.component';

describe('AlertScannersComponent', () => {
  let component: AlertScannersComponent;
  let fixture: ComponentFixture<AlertScannersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AlertScannersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AlertScannersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
