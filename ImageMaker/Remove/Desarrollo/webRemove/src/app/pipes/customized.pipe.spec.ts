import { CustomizedPipe } from './customized.pipe';

describe('CustomizedPipe', () => {
  it('create an instance', () => {
    const pipe = new CustomizedPipe();
    expect(pipe).toBeTruthy();
  });
});
