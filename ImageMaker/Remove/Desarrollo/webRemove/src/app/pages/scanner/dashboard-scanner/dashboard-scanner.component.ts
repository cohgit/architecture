import {Component, Input, OnInit} from '@angular/core';
import {NgbProgressbarConfig} from '@ng-bootstrap/ng-bootstrap';
import {ScannerDashboard} from '../../../interfaces/IScannerDashboard';
import {TranslateService} from '@ngx-translate/core';
import {UtilService} from '../../../helpers/util.service';
import {SessionService} from '../../../helpers/session.service';


@Component({
  selector: 'app-dashboard-scanner',
  templateUrl: './dashboard-scanner.component.html',
  styleUrls: ['./dashboard-scanner.component.css']
})
export class DashboardScannerComponent implements OnInit {
  @Input() dashboardData: ScannerDashboard;
  @Input() expandBox: boolean;
  @Input() scannerInfo: any;
  isTransform = false;
  showFlag = true;
  istrue = true;
  isFalse = false;
  legendBelow = 'below';
  legendLabel = '';
  minRep = -100;
  maxRep = 100;
  minVis = 0;
  GREEN = '#54E887';
  RED = '#FF504A';
  YELLOW = '#E3D042';
  BLUE = '#3C9BE8';
  GRAY = '#bcd3df';
  totalImpulsesReached = 0;
  colorGreen = {
    domain: [this.GREEN]
  };
  colorRed = {
    domain: [this.RED]
  };
  colorYellow = {
    domain: [this.YELLOW]
  };
  colorSchemeFeelings = {
    domain: [this.GREEN, this.RED, this.BLUE, this.GRAY]
  };

  color: {
    green: boolean;
    yellow: boolean;
    red: boolean;
  };
  copyArrayNegative: Array<any>;
  copyArrayNew: Array<any>;
  pages: any = {};

  expande = false;
  isOneShot = false;
  totalSnipGoal = 0;
  plansGoal = 0;

  /**
   *  Scanner dashboard tab
   * @param config
   * @param translate
   * @param utilService
   * @param session
   */
  constructor(private config: NgbProgressbarConfig, public translate: TranslateService,
              public utilService: UtilService, public session: SessionService) {
    config.max = 1000;
    config.striped = true;
    config.animated = true;
    config.type = 'success';
    config.height = '20px';
  }

  ngOnInit(): void {
    if (this.scannerInfo.type === 'transform') {
      this.isTransform = true;
      this.calculateTotalSnippets(this.scannerInfo);
      this.countImpulses(this.dashboardData?.v2?.impulseData);
      this.plansGoal = this.scannerInfo?.restrictions?.detail?.target_page;
    }
    if (this.scannerInfo.type === 'one_shot') {
      this.isOneShot = true;
    }
  }

  /**
   * calculate the total of snippets that recives  the scanner
   * @param conf
   */
  calculateTotalSnippets(conf: any) {
    if (conf?.justTrackingURLs && conf?.justTrackingURLs.length !== 0) {
      const countries = conf.configuration.countries.length;
      const types = conf.configuration.lstSearchTypes.length;
      const key = conf.keywords.length;
      const url = conf?.justTrackingURLs.length;
      this.totalSnipGoal = countries * types * key * url;
    }
  }

  /**
   * color for class
   * @param code
   */
  checkColor(code: string) {
    if (code === 'neutral') {
      return 'coloAmarillo';
    }
    if (code === 'good') {
      return 'colorVerde';
    }
    if (code === 'bad') {
      return 'colorRojo';
    }
  }

  /**
   * for paginator
   * @param pageOfItems
   * @param page
   */
  onChangePage(pageOfItems: Array<any>, page: string) {
    this.pages[page] = pageOfItems;
  }
/**
 * count how many impulses exists
 * @param data
 */
  countImpulses(data){
   // console.log('data', data);
    if (data){
      data.results.forEach(result => {
        if (result.targetReached){
          this.totalImpulsesReached = ++this.totalImpulsesReached;
        }
      });
     // console.log('totalImpulsesReached: ', this.totalImpulsesReached)
    }

  }
  /**
   * format day for dashboard graph
   * @param val
   */
  dateTickFormatting(val: any): string {
    return new Date(val).toLocaleDateString();
  }

  /**
   * open a web based on a line chart
   * @param data
   */
  onSelectLineChart(data): void {

    window.open(data.series, '_blank');
  }

  /**
   * on change page for negative content
   * DEPRECATED we used gadget for this module
   * @param pageOfItems
   */
  onChangePageNegative(pageOfItems: Array<any>): void {
    this.copyArrayNegative = pageOfItems;
  }

  /**
   * on change page for new content
   * DEPRECATED we used gadget for this module
   * @param pageOfItems
   */
  onChangePageNew(pageOfItems: Array<any>): void {
    this.copyArrayNew = pageOfItems;
  }

}
