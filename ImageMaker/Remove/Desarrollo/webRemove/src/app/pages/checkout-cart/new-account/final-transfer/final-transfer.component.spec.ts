import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FinalTransferComponent } from './final-transfer.component';

describe('FinalTransferComponent', () => {
  let component: FinalTransferComponent;
  let fixture: ComponentFixture<FinalTransferComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FinalTransferComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FinalTransferComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
