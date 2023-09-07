import {Component, Input, OnInit} from '@angular/core';
import {NgbProgressbarConfig} from '@ng-bootstrap/ng-bootstrap';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-dash-report',
  templateUrl: './dash-report.component.html',
  styleUrls: ['./dash-report.component.css']
})
export class DashReportComponent implements OnInit {
  @Input() infoDash: any;
  showFlag = true;
  istrue = true;
  isFalse = false;
  legendBelow = 'below';
  legendLabel = '';
  minRep = -100;
  maxRep = 100;
  minVis = 0;
  colorGreen = {
    domain: ['#54E887']
  };
  colorRed = {
    domain: ['#FF504A']
  };
  colorYellow = {
    domain: ['#e1e314']
  };
  colorSchemeFeelings = {
    domain: ['#54E887', '#FF504A', '#3C9BE8', '#bcd3df']
  };
  colorSchemeNeutral = {
    domain: ['#8720AB', '#ffc4ab', '#E3B314', '#0e6424', '#0e6424', '#847770']
  };
  color: {
    green: boolean;
    yellow: boolean;
    red: boolean;
  };
  pageOfTrack: Array<any>;
  pageOfUrl: Array<any>;

  constructor(private config: NgbProgressbarConfig, public translate: TranslateService) {
    config.max = 1000;
    config.striped = true;
    config.animated = true;
    config.type = 'success';
    config.height = '20px';

  }

  ngOnInit(): void {

  }

  checkColor(code: string) {
    if (code === 'neutral'){
      return 'coloAmarillo';
    }
    if (code === 'good'){
      return 'colorVerde';
    }
    if (code === 'bad'){
      return 'colorRojo';
    }
  }

  onChangePageUrl(pageOfItems: Array<any>) {
    this.pageOfUrl = pageOfItems;
  }
  onChangePageTracking(pageOfItems: Array<any>) {
    this.pageOfTrack = pageOfItems;
  }

  /**
   * format day for dashboard graph
   * @param val
   */
  dateTickFormatting(val: any): string {
    return new Date(val).toLocaleDateString();
  }


  onSelectLineChart(data): void {
    window.open(data.series, '_blank');
  }

}
