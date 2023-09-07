import { TestBed } from '@angular/core/testing';

import { TrackingWordService } from './tracking-word.service';

describe('TrackingWordService', () => {
  let service: TrackingWordService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TrackingWordService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
