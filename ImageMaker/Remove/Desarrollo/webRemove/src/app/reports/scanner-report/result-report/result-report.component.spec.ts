import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ResultReportComponent } from './result-report.component';

describe('ResultReportComponent', () => {
  let component: ResultReportComponent;
  let fixture: ComponentFixture<ResultReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ResultReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ResultReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
