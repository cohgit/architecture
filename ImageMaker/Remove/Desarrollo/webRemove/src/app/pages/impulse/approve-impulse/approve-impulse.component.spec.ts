import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApproveImpulseComponent } from './approve-impulse.component';

describe('ApproveImpulseComponent', () => {
  let component: ApproveImpulseComponent;
  let fixture: ComponentFixture<ApproveImpulseComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApproveImpulseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApproveImpulseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
