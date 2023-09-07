import { TestBed } from '@angular/core/testing';

import { ForbiddenWordService } from './forbidden-word.service';

describe('ForbiddenWordService', () => {
  let service: ForbiddenWordService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ForbiddenWordService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
