import { TestBed } from '@angular/core/testing';

import { SessionLogService } from './session-log.service';

describe('SessionLogService', () => {
  let service: SessionLogService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SessionLogService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
