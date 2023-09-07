import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewEditPlanComponent } from './new-edit-plan.component';

describe('NewEditPlanComponent', () => {
  let component: NewEditPlanComponent;
  let fixture: ComponentFixture<NewEditPlanComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewEditPlanComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewEditPlanComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
