import { TestBed } from '@angular/core/testing';

import { DeindexationService } from './deindexation.service';

describe('DeindexationService', () => {
  let service: DeindexationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DeindexationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
