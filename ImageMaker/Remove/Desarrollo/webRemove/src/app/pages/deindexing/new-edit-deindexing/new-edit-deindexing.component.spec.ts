import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewEditDeindexingComponent } from './new-edit-deindexing.component';

describe('NewEditDeindexingComponent', () => {
  let component: NewEditDeindexingComponent;
  let fixture: ComponentFixture<NewEditDeindexingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewEditDeindexingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewEditDeindexingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
