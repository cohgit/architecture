import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {UtilService} from "../../../helpers/util.service";


@Component({
  selector: 'app-active-licences',
  templateUrl: './active-licences.component.html',
  styleUrls: ['./active-licences.component.css']
})
export class ActiveLicencesComponent implements OnInit {
  @Input() infoPlan: any = {};
  @Output() selectedTransform = new EventEmitter<any>();
  @Output() selectedMonitor = new EventEmitter<any>();
  @Output() warningTransform = new EventEmitter<any>();
  @Output() warningMonitor = new EventEmitter<any>();


  totalMonitor = 0;
  totalTransform = 0;
  migrateTransforma = false;
  migrateMonitor = false;

  copyArrayMonitor: Array<any>;
  copyArrayTransform: Array<any>;

  total_transform_selected = 0;
  total_monitor_selected = 0;
  noTransform = false;
  noMonitor = false;
  monitorActualLicences = 0;
  monitorNewLicence = 0;
  transformActualLicences = 0;
  transformNewLicences = 0;


  constructor(public utilService: UtilService) {
  }

  ngOnInit(): void {
    this.checkDowngrade();
  }

  /**
   * show all actives scaner, filtered by type
   */
  checkDowngrade() {
    if (this.infoPlan?.scannersToMigrateRecurrent?.length > 0) {
      if (!this.infoPlan?.actualPlan?.total_monitor_licenses) {
        this.monitorActualLicences = 0;
      } else {
        this.monitorActualLicences = this.infoPlan?.actualPlan?.total_monitor_licenses;
      }
      if (this.infoPlan?.newPlan?.access_monitor) {
        if (!this.infoPlan?.newPlan?.total_monitor_licenses) {
          this.monitorNewLicence = 0;
        } else {
          this.monitorNewLicence = this.infoPlan?.newPlan?.total_monitor_licenses;
        }
        this.totalMonitor = this.infoPlan.scannersToMigrateRecurrent.length - this.monitorNewLicence;
        this.infoPlan.scannersToMigrateRecurrent.forEach(scan => {
          scan.checked = true;
        });
        this.copyArrayMonitor = this.infoPlan.scannersToMigrateRecurrent;
        this.migrateMonitor = true;
      } else {
        this.noMonitor = true;
      }
    }


    if (this.infoPlan?.scannersToMigrateTransforma?.length > 0) {
      if (!this.infoPlan?.actualPlan?.total_transforma_licenses) {
        this.transformActualLicences = 0;
      } else {
        this.transformActualLicences = this.infoPlan?.actualPlan?.total_transforma_licenses;
      }
      if (this.infoPlan?.newPlan?.access_transforma) {
        if (!this.infoPlan?.newPlan?.total_transforma_licenses) {
          this.transformNewLicences = 0;
        } else {
          this.transformNewLicences = this.infoPlan?.newPlan?.total_transforma_licenses;
        }

        this.totalTransform = this.infoPlan.scannersToMigrateTransforma.length - this.transformNewLicences;
        this.infoPlan.scannersToMigrateTransforma.forEach(scan => {
          scan.checked = true;
        });
        this.copyArrayTransform = this.infoPlan.scannersToMigrateTransforma;
        this.migrateTransforma = true;
      } else {
        this.noTransform = true;
      }
    }
    const checkMonitor = this.checkWarning(this.noMonitor, this.migrateMonitor);
    this.warningMonitor.emit(checkMonitor);
    const checkTrans = this.checkWarning(this.noTransform, this.migrateTransforma);
    this.warningTransform.emit(checkTrans);
  }

  checkWarning(no: boolean, migrate: boolean) {
    return no && !migrate;
  }

  validateLimitTransfrom(): void {
    let warform = false;
    this.total_transform_selected = this.validateLimit(this.infoPlan.scannersToMigrateTransforma);
    this.selectedTransform.emit(this.total_transform_selected);
    if (this.noTransform && !this.migrateTransforma) {
      warform = true;
    }
    this.warningTransform.emit(warform);
  }


  validateLimitMonitor(): void {
    this.total_monitor_selected = this.validateLimit(this.infoPlan.scannersToMigrateRecurrent);
    this.selectedMonitor.emit(this.total_monitor_selected);
  }

  private validateLimit(listScanners: any[]): number {
    let checkeds = 0;

    if (listScanners) {
      listScanners.forEach(scan => {
        checkeds = scan.checked ? checkeds + 1 : checkeds;
      })
    }

    return checkeds;
  }


  /**paginator
   *  change paginator
   * @param pageOfItems
   */
  onChangePageMonitor(pageOfItems: Array<any>): void {
    this.copyArrayMonitor = pageOfItems;
  }

  /** paginator
   *  change paginator
   * @param pageOfItems
   */
  onChangePageTransform(pageOfItems: Array<any>): void {
    this.copyArrayTransform = pageOfItems;
  }
}
