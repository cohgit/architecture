import { PaymentsMethodsPipe } from './payments-methods.pipe';

describe('PaymentsMethodsPipe', () => {
  it('create an instance', () => {
    const pipe = new PaymentsMethodsPipe();
    expect(pipe).toBeTruthy();
  });
});
