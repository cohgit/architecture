import { TestBed } from '@angular/core/testing';

import { XternalCommonService } from './xternal-common.service';

describe('XternalCommonService', () => {
  let service: XternalCommonService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(XternalCommonService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
