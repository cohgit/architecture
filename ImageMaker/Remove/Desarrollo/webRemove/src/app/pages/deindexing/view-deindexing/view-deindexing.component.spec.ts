import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewDeindexingComponent } from './view-deindexing.component';

describe('ViewDeindexingComponent', () => {
  let component: ViewDeindexingComponent;
  let fixture: ComponentFixture<ViewDeindexingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewDeindexingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewDeindexingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
