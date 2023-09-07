import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfigOneShotComponent } from './config-scanner.component';

describe('ConfigOneShotComponent', () => {
  let component: ConfigOneShotComponent;
  let fixture: ComponentFixture<ConfigOneShotComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConfigOneShotComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfigOneShotComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
