import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PoliticsModalComponent } from './politics-modal.component';

describe('PoliticsModalComponent', () => {
  let component: PoliticsModalComponent;
  let fixture: ComponentFixture<PoliticsModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PoliticsModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PoliticsModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
