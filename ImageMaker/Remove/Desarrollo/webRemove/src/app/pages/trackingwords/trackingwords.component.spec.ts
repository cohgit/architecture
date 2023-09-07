import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TrackingwordsComponent } from './trackingwords.component';

describe('TrackingwordsComponent', () => {
  let component: TrackingwordsComponent;
  let fixture: ComponentFixture<TrackingwordsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TrackingwordsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TrackingwordsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
