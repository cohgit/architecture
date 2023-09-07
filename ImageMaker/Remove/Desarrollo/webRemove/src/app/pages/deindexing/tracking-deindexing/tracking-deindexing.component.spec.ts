import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TrackingDeindexingComponent } from './tracking-deindexing.component';

describe('TrackingDeindexingComponent', () => {
  let component: TrackingDeindexingComponent;
  let fixture: ComponentFixture<TrackingDeindexingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TrackingDeindexingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TrackingDeindexingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
