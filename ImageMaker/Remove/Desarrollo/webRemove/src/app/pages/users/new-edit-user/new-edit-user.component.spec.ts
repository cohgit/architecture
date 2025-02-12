import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewEditUserComponent } from './new-edit-user.component';

describe('NewEditUserComponent', () => {
  let component: NewEditUserComponent;
  let fixture: ComponentFixture<NewEditUserComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewEditUserComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewEditUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
