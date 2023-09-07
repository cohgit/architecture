import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HistoryAuditUserComponent } from './history-audit-user.component';

describe('HistoryAuditUserComponent', () => {
  let component: HistoryAuditUserComponent;
  let fixture: ComponentFixture<HistoryAuditUserComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HistoryAuditUserComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HistoryAuditUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
