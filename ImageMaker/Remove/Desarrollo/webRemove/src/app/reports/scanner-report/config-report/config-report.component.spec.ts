import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfigReportComponent } from './config-report.component';

describe('ConfigReportComponent', () => {
  let component: ConfigReportComponent;
  let fixture: ComponentFixture<ConfigReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConfigReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfigReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
