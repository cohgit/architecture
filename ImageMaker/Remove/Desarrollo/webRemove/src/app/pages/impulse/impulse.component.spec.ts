import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ImpulseComponent } from './impulse.component';

describe('ImpulseComponent', () => {
  let component: ImpulseComponent;
  let fixture: ComponentFixture<ImpulseComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ImpulseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ImpulseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
