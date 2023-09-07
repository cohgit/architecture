import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ResultScannerComponent } from './result-scanner.component';

describe('ResultScannerComponent', () => {
  let component: ResultScannerComponent;
  let fixture: ComponentFixture<ResultScannerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ResultScannerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ResultScannerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
