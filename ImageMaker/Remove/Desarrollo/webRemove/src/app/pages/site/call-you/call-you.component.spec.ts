import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CallYouComponent } from './call-you.component';

describe('CallYouComponent', () => {
  let component: CallYouComponent;
  let fixture: ComponentFixture<CallYouComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CallYouComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CallYouComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
