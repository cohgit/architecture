import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InfoClientsComponent } from './info-clients.component';

describe('InfoClientsComponent', () => {
  let component: InfoClientsComponent;
  let fixture: ComponentFixture<InfoClientsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InfoClientsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InfoClientsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
