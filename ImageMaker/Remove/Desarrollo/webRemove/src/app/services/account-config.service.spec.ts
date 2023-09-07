import { TestBed } from '@angular/core/testing';

import { AccountConfigService } from './account-config.service';

describe('AccountConfigService', () => {
  let service: AccountConfigService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AccountConfigService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
