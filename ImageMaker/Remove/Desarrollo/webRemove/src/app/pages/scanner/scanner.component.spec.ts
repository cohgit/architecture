import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ScannerOneShotComponent } from './scanner.component';

describe('ScannerOneShotComponent', () => {
  let component: ScannerOneShotComponent;
  let fixture: ComponentFixture<ScannerOneShotComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ScannerOneShotComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ScannerOneShotComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
