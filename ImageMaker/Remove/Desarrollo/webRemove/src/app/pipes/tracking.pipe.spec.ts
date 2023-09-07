import { TrackingPipe } from './tracking.pipe';

describe('TrackingPipe', () => {
  it('create an instance', () => {
    const pipe = new TrackingPipe();
    expect(pipe).toBeTruthy();
  });
});
