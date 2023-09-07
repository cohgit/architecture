import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewEditImpulseComponent } from './new-edit-impulse.component';

describe('NewEditImpulseComponent', () => {
  let component: NewEditImpulseComponent;
  let fixture: ComponentFixture<NewEditImpulseComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewEditImpulseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewEditImpulseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
