import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ScannerReportComponent } from './scanner-report.component';

describe('ScannerReportComponent', () => {
  let component: ScannerReportComponent;
  let fixture: ComponentFixture<ScannerReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ScannerReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ScannerReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
