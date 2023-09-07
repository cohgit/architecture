import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AlertSectionsComponent } from './alert-sections.component';

describe('AlertSectionsComponent', () => {
  let component: AlertSectionsComponent;
  let fixture: ComponentFixture<AlertSectionsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AlertSectionsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AlertSectionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
