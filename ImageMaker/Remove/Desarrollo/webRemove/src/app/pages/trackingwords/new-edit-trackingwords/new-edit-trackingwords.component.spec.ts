import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewEditTrackingwordsComponent } from './new-edit-trackingwords.component';

describe('NewEditTrackingwordsComponent', () => {
  let component: NewEditTrackingwordsComponent;
  let fixture: ComponentFixture<NewEditTrackingwordsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewEditTrackingwordsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewEditTrackingwordsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
