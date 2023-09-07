import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SuccessDemoComponent } from './success-demo.component';

describe('SuccessDemoComponent', () => {
  let component: SuccessDemoComponent;
  let fixture: ComponentFixture<SuccessDemoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SuccessDemoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SuccessDemoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
