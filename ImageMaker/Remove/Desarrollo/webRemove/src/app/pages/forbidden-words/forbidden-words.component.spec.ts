import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ForbiddenWordsComponent } from './forbidden-words.component';

describe('ForbiddenWordsComponent', () => {
  let component: ForbiddenWordsComponent;
  let fixture: ComponentFixture<ForbiddenWordsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ForbiddenWordsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ForbiddenWordsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
