import { TestBed } from '@angular/core/testing';

import { ImpulseVariableService } from './impulse-variable.service';

describe('ImpulseVariableService', () => {
  let service: ImpulseVariableService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ImpulseVariableService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
