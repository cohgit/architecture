import { Component, Input, OnInit } from '@angular/core';
import { STEPPER_GLOBAL_OPTIONS } from '@angular/cdk/stepper';
import { MatSidenav } from '@angular/material/sidenav';
import { CommonService } from '../../services/common.service';
import { AccountConfigService } from 'src/app/services/account-config.service';
import { SessionService } from 'src/app/helpers/session.service';
import { ConfirmChangeComponent } from './confirm-change/confirm-change.component';
import { MatDialog } from '@angular/material/dialog';
import { CustomPlanComponent } from '../custom-plan/custom-plan.component';
import { UtilService } from 'src/app/helpers/util.service';
import { SessionLogService } from '../../services/session-log.service';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css'],
  providers: [
    {
      provide: STEPPER_GLOBAL_OPTIONS,
      useValue: { displayDefaultIndicatorType: false },
    },
  ],
})
export class SidebarComponent implements OnInit {
  @Input() sidenav: MatSidenav;

  info: any;
  //suggested = [];
  //allsuggested = [];
  nearestValue = (arr, val) =>
    arr.reduce(
      (p, n) => (Math.abs(p) > Math.abs(n - val) ? n - val : p),
      Infinity
    ) + val;

  /**
   *
   * @param commonService
   * @param configService
   * @param session
   * @param dialog
   * @param utilService
   * @param logoutService
   */
  constructor(
    private commonService: CommonService,
    private configService: AccountConfigService,
    private session: SessionService,
    private dialog: MatDialog,
    public utilService: UtilService,
    private logoutService: SessionLogService
  ) {}

  ngOnInit(): void {
    // FIX ME: Bootstrap no es compatible con "@popperjs/core": "^2.4.4", Downgrade en package.json a 1.11.0
    this.sidenav.openedChange.subscribe(() => {
      if (this.sidenav.opened && !this.info) {
        this.loadInfo();
      }
    });
  }

  loadInfo(): void {
    this.commonService.listClientsPlans((response) => {
      this.info = response;
      this.info.plansAvailables = this.utilService.sortArrayValue(
        this.info.plansAvailables,
        'cost'
      );
      //  this.info.plansCustomized = this.utilService.sortArrayValue(this.info.plansCustomized, 'cost');
      this.info.plansCustomized.reverse();
      this.checkCrossedSuggested();
    });
  }

  /**
   * close sidebar
   */
  closeSidebar(): void {
    this.sidenav.close();
  }

  /**
   * check if a item is repeat in all in suggest
   * @param suggest
   * @param all
   */

  /*removeItem(suggest: any, all: any): void {
    if (this.suggested.length !== 0 && this.allsuggested.length !== 0){
      all.forEach(item => {
        suggest.forEach(only => {
          if (item.id === only.id){
            const index = this.allsuggested.findIndex(i => i === item);
            this.allsuggested.splice(index, 1);
          }
        });
      });
    }
  }*/

  /**
   * Suggest next in cost value.
   */
  checkCrossedSuggested(): void {
    let filtred = [];
    if (this.info.plansCustomized.length === 0) {
      filtred = this.info.plansAvailables.filter(
        (u) => u.cost > this.info.actualPlan.cost
      );

      if (filtred.length > 0) {
        this.info.plansCustomized.push(filtred[0]);
      }
    }
  }

  changePlan(detailPlan: any): void {
    this.info.newPlan = detailPlan;

    const dialogRef = this.dialog.open(ConfirmChangeComponent, {
      closeOnNavigation: true,
      disableClose: true,
      data: {
        info: this.info,
        sidenav: this.sidenav,
      },
    });
    dialogRef.afterClosed().subscribe((confirmed) => {
      if (confirmed) {
        console.log('lo que va al servicio', this.info);
        this.configService.updatePlan(this.info, () => {
          this.closeSidebar();
          this.logout();
        });
      }
    });
  }

  /**
   * Logout service
   */
  logout(): void {
    this.logoutService.logout(() => this.session.destroySession(), {
      errorFunction: () => this.session.destroySession(),
    });
  }

  customPlan() {
    this.dialog.open(CustomPlanComponent, {
      width: '70%',
      closeOnNavigation: true,
    });
  }
}
