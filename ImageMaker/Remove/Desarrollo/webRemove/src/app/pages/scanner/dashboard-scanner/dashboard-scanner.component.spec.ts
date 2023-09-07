import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardScannerComponent } from './dashboard-scanner.component';

describe('DashboardScannerComponent', () => {
  let component: DashboardScannerComponent;
  let fixture: ComponentFixture<DashboardScannerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DashboardScannerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DashboardScannerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
