import { TestBed } from '@angular/core/testing';

import { ScannerCommentsService } from './scanner-comments.service';

describe('ScannerCommentsService', () => {
  let service: ScannerCommentsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ScannerCommentsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
