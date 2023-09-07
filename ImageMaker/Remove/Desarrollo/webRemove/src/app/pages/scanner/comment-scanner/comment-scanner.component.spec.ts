import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CommentScannerComponent } from './comment-scanner.component';

describe('CommentScannerComponent', () => {
  let component: CommentScannerComponent;
  let fixture: ComponentFixture<CommentScannerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CommentScannerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommentScannerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
