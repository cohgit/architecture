import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DeindexingComponent } from './deindexing.component';

describe('DeindexingComponent', () => {
  let component: DeindexingComponent;
  let fixture: ComponentFixture<DeindexingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DeindexingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeindexingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
