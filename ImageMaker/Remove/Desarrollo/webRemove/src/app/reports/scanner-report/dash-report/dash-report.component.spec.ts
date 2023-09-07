import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DashReportComponent } from './dash-report.component';

describe('DashReportComponent', () => {
  let component: DashReportComponent;
  let fixture: ComponentFixture<DashReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DashReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DashReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
