import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { KeywordTableComponent } from './keyword-table.component';

describe('KeywordTableComponent', () => {
  let component: KeywordTableComponent;
  let fixture: ComponentFixture<KeywordTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ KeywordTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(KeywordTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
