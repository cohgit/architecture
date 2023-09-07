import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TransformSectionComponent } from './transform-section.component';

describe('TransformSectionComponent', () => {
  let component: TransformSectionComponent;
  let fixture: ComponentFixture<TransformSectionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TransformSectionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TransformSectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
