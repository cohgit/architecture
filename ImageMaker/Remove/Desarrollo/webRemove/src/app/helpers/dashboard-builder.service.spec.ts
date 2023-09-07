import { TestBed } from '@angular/core/testing';

import { DashboardBuilderService } from './dashboard-builder.service';

describe('DashboardBuilderService', () => {
  let service: DashboardBuilderService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DashboardBuilderService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
