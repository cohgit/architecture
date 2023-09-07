import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmScannerComponent } from './confirm-scanner.component';

describe('ConfirmScannerComponent', () => {
  let component: ConfirmScannerComponent;
  let fixture: ComponentFixture<ConfirmScannerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConfirmScannerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfirmScannerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
