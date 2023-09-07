export interface IScannerDashboard {
  reputation?: {
    name: string,
    value: number,
    extra: {
      code: string
      colorScheme: {
        domain: string[]
      }
    }
  }[],
  visibility?: {
    max_value: number,
    value: number,
    previous_value: number
  },
  totalResults?: number,
  assignedFeelingsPie?: IScannerDashboardItem[],
  trackingWordsFeelingsBar?: IScannerGraph[],
  urlsInTimeLineal?: IURLTimeLineal[],
  resultsByTrackingWord?: IScannerDashboardResult[],
  resultsByUrl?: IScannerDashboardResult[],
  genericResults?: {
    title: string,
    snippet: string,
    link: string,
    position: number,
    trending: string,
    date: string,
    trackingWords: string[],
    feeling: string
  }[]
  impulseTracking?: any[],
  urlTracking?: any[],

  v2?: any
}

interface IURLTimeLineal {
  country: string,
  keyword: string,
  type: string,
  minYAxis?: number,
  maxYAxis?: number,
  graph: IScannerGraph[]
}
interface IScannerGraph {
  name: string,
  series: IScannerDashboardItem[]
}
interface IScannerDashboardItem {
  name: string,
  value: number,
  extra: {
    code: string
  }
}
interface IScannerDashboardResult {
  [id: string]: {
    title: string,
    link: string,
    position: number
  }[]
}

export class ScannerDashboard implements IScannerDashboard {
  reputation?: { name: string; value: number; extra: { code: string; colorScheme: {domain: string[]}}; }[];
  visibility?: { max_value: number; value: number; previous_value: number; };
  totalResults?: number;
  assignedFeelingsPie?: IScannerDashboardItem[];
  trackingWordsFeelingsBar?: IScannerGraph[];
  urlsInTimeLineal?: IURLTimeLineal[];
  resultsByTrackingWord?: IScannerDashboardResult[];
  resultsByUrl?: IScannerDashboardResult[];
  genericResults?: { title: string; snippet: string; link: string; position: number; trending: string; date: string; trackingWords: string[]; feeling: string; }[];
  impulseTracking?: any[];
  urlTracking?: any[];
  v2?: any;

  constructor () {}
}