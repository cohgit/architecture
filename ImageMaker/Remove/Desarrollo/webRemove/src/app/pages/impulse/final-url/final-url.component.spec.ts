import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FinalURLComponent } from './final-url.component';

describe('FinalURLComponent', () => {
  let component: FinalURLComponent;
  let fixture: ComponentFixture<FinalURLComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FinalURLComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FinalURLComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
