import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdvaceReportComponent } from './advace-report.component';

describe('AdvaceReportComponent', () => {
  let component: AdvaceReportComponent;
  let fixture: ComponentFixture<AdvaceReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdvaceReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdvaceReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
