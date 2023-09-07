import { Component, Input, OnInit } from '@angular/core';
import { DashboardBuilderService } from 'src/app/helpers/dashboard-builder.service';
import { UtilService } from 'src/app/helpers/util.service';

@Component({
  selector: 'app-transform-section',
  templateUrl: './transform-section.component.html',
  styleUrls: ['./transform-section.component.css'],
})
export class TransformSectionComponent implements OnInit {
  GREEN = '#54E887';
  RED = '#FF504A';
  YELLOW = '#E3D042';
  BLUE = '#3C9BE8';
  GRAY = '#bcd3df';

  @Input() data;
  @Input() scannerInfo;
  @Input() totalSnipGoal;
  @Input() plansGoal;
  @Input() isImpulse;

  scannerType = '';
  paginatorClass = 'paginator_' + Math.floor(Math.random() * 1000000);
  successGoals = 0;
  totalSuccessGoals = 0;
  inProcess = 0;
  totalImpulsesReached = 0;
  istrue = true;
  showGoal = false;
  sortDirection = false;
  copyArray: Array<any>;
  colorSchemeFeelings = {
    domain: [this.GREEN, this.RED, this.BLUE, this.GRAY],
  };
  // progressBar = 0;
  /**
   * manage impulse and content to delete data
   * @param utilService
   * @param db
   */
  constructor(
    public utilService: UtilService,
    private db: DashboardBuilderService
  ) {}
  ngOnInit(): void {
    this.checksuccess();
    this.scannerType = this.scannerInfo?.type;
    if (this.scannerType === 'transform') {
      this.showGoal = true;
    }
    this.db.createUrlTimeLinealData(this.data, this.copyArray);
    //console.log('data', this.data);
  }
  /*+
  Count total of goals reached (or not) to show on the cards and in the message in content to delete
   */
  checksuccess() {
    let totalTargetReached = 0;
    this.totalImpulsesReached = 0;
    this.data.results.forEach((result) => {
      if (result.targetReached) {
        totalTargetReached = ++totalTargetReached;
      }
    });
    if (this.totalSnipGoal && this.data?.results.length) {
      this.successGoals = this.totalSnipGoal - this.data?.results.length; // Todas las URL que no aparecen
      this.totalSuccessGoals = this.successGoals + totalTargetReached; // URL que no aprecen más las que alcanzaron el objetivo
      this.inProcess = this.totalSnipGoal - this.totalSuccessGoals; // Total de URl que faltan por procesar
    }
    if (this.isImpulse) {
      this.data.results.forEach((result) => {
        if (result.targetReached) {
          this.totalImpulsesReached = ++this.totalImpulsesReached;
        }
      });
    }
    /*  this.calculateProgresss(
      this.totalSnipGoal,
      this.totalSuccessGoals,
      this.inProcess
    );*/
  }
//DEPRECATED
  /* calculateProgresss(snip, tota, proc) {
    if (proc == 0) {
      this.progressBar = 100;
    }
    if (snip === proc) {
      this.progressBar = 0;
    } else {
      this.progressBar = (tota * 100) / snip;
    }
  }*/

  /**
   *
   * Function to open link from line chart in other page
   * @param data
   */
  onSelectLineChart(data): void {
    window.open(data.extra.link, '_blank');
  }

  /**
   * format day for dashboard graph
   * @param val
   */
  dateTickFormatting(val: any): string {
    return new Date(val).toLocaleDateString();
  }

  /**
   * for paginator
   * @param pageOfItems
   */
  onChangePage(pageOfItems: Array<any>) {
    this.copyArray = pageOfItems;
    this.db.createUrlTimeLinealData(this.data, this.copyArray);
  }
  /**
   * Order table Items
   * @param colName
   */
  sortFunction(colName: string): void {
    const result = this.utilService.sortTable(
      colName,
      true,
      this.data?.results,
      this.sortDirection
    );
    this.data.results = result.array;
    this.sortDirection = result.direction;
    this.utilService.changeSelectedPageToFirst(this.paginatorClass);
  }
}
