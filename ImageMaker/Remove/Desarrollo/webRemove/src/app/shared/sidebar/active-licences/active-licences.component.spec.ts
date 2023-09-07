import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ActiveLicencesComponent } from './active-licences.component';

describe('ActiveLicencesComponent', () => {
  let component: ActiveLicencesComponent;
  let fixture: ComponentFixture<ActiveLicencesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ActiveLicencesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ActiveLicencesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
