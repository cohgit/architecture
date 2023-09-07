import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TableTrackingProgressComponent } from './table-tracking-progress.component';

describe('TableTrackingProgressComponent', () => {
  let component: TableTrackingProgressComponent;
  let fixture: ComponentFixture<TableTrackingProgressComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TableTrackingProgressComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TableTrackingProgressComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
