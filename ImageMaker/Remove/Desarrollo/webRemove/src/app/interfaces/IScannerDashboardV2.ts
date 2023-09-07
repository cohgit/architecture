export interface IScannerDashboardV2 {
  removeData?: IScannerTransformData,
  impulseData?: IScannerTransformData,

  keywordsByTW: IScannerKeywordsElementList[],
  keywordsByURL: IScannerKeywordsElementList[],
  keywordsByImpulse?: IScannerKeywordsElementList[],

  graphs?: {
    feelingPie?: any[],
    keywordBar?: any[]
  },

  alerts?: {
    negative: any[],
    new: any[]
  }
}

export interface IURLTimeLineal {
  minYAxis?: number,
  maxYAxis?: number,
  schemaColors?: any,
  graph: IScannerGraph[]
}
export interface IScannerGraph {
  name: string,
  series: IScannerDashboardItem[]
}
export interface IScannerDashboardItem {
  name: string,
  value: number,
  extra: {
    code: string
  }
}
export interface IScannerTransformData {
  target?: number,
  results?: {
    snippet: any,
    search_type: string,
    country: string,
    keyword: string,
    targetReached?: boolean
  }[],
  timeline?: IURLTimeLineal  
}
export interface IScannerKeywordsElementList {
  snippet: any,
  keyword: string,
  visibility: number
}

export class ScannerDashboardV2 implements IScannerDashboardV2 {
  removeData?: IScannerTransformData;
  impulseData?: IScannerTransformData;

  keywordsByTW: [];
  keywordsByURL: [];
  keywordsByImpulse?: IScannerKeywordsElementList[];

  graphs?: {
    feelingPie?: any[],
    keywordBar?: any[]
  };

  alerts?: {
    negative: any[],
    new: any[]
  };

  constructor () {}
}