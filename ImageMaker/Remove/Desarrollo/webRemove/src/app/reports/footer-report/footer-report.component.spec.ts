import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FooterReportComponent } from './footer-report.component';

describe('FooterReportComponent', () => {
  let component: FooterReportComponent;
  let fixture: ComponentFixture<FooterReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FooterReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FooterReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
