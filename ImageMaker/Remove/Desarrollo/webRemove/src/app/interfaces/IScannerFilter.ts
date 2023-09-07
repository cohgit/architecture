export interface IScannerFilter {
  applieds?: string[],
  keywords?: any,
  suggested_keywords?: any,
  feelings?: any[],
  tracking_words?: any[],
  urls?: any[],
  countries?: any[];
  search_types?: string[],
  pages?: number[],
  init_date?: any,
  end_date?: any,
  isNew: boolean
}

export class ScannerFilter implements IScannerFilter {
  applieds?: string[];
  keywords?: any;
  suggested_keywords?: any;
  feelings?: any[];
  tracking_words?: any[];
  urls?: any[];
  countries?: any[];
  search_types?: string[];
  pages?: number[];
  init_date?: any;
  end_date?: any;
  isNew: boolean;

  constructor () { }
}
