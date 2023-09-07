import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DynamicDisplayComponent } from './dynamic-display.component';

describe('DynamicDisplayComponent', () => {
  let component: DynamicDisplayComponent;
  let fixture: ComponentFixture<DynamicDisplayComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DynamicDisplayComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DynamicDisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
