import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewEditForbiddenWordsComponent } from './new-edit-forbidden-words.component';

describe('NewEditForbiddenWordsComponent', () => {
  let component: NewEditForbiddenWordsComponent;
  let fixture: ComponentFixture<NewEditForbiddenWordsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewEditForbiddenWordsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewEditForbiddenWordsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
