import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewEditClientComponent } from './new-edit-client.component';

describe('NewEditClientComponent', () => {
  let component: NewEditClientComponent;
  let fixture: ComponentFixture<NewEditClientComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewEditClientComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewEditClientComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
