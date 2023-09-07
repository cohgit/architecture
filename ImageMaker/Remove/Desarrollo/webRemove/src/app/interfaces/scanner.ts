export interface IScanner {
  id: number;
  lstkeywords: any[];
  lstCountries: any[];
  lstTerms: any[];
  lstURL: any[];
}

export class Scanner implements IScanner {
  id = 0;
  lstkeywords = [];
  lstCountries = [];
  lstTerms = [];
  lstURL = [];
}
