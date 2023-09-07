

export interface ITrackingWords {
  id?: number;
  active?: boolean;
  feeling?: string;
  feelingObj?: {};
  name?: string;​​​
  word?: string;
}
export class TrackingWords implements ITrackingWords {
  id?: null;
  active?: true;
  feeling?: '';
  feelingObj?: null;
  name?: '';​​​
  word?: '';
}
