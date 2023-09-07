import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ModalService } from 'src/app/helpers/modal.service';
import { SessionService } from 'src/app/helpers/session.service';
import { ImpulseService } from 'src/app/services/impulse.service';
import { ScannerService } from 'src/app/services/scanner.service';
import { UtilService } from '../../../helpers/util.service';
import * as moment from 'moment';
import { CommentScannerComponent } from '../comment-scanner/comment-scanner.component';
import { MatDialog } from '@angular/material/dialog';
import { AlertScannersComponent } from '../alert-scanners/alert-scanners.component';
import { AccountConfigService } from 'src/app/services/account-config.service';
import { ClientService } from 'src/app/services/client.service';

@Component({
  selector: 'app-global-view',
  templateUrl: './global-view.component.html',
  styleUrls: ['./global-view.component.css'],
})
export class GlobalViewComponent implements OnInit {
  uuid_client: string;
  id_client: any;
  filteredScanner = [];
  type: string;
  idClientPlan: number; // Not in use at this moment
  offset = new Date().getTimezoneOffset();
  // Table controller params
  table: any = { records: [] };
  copyArray: Array<any>;
  /* objeto a enviar al filtro */
  filter = {
    name: '',
  };
  sortDirection = false;
  isOneShot = false;
  isMonitor = false;
  isTransform = false;
  limitScanners: number;
  occupieds = 0;
  paginatorClass = 'paginator';
  clientAlerts = true;
  planDetail = {};
  clientData = {};
  progressBar = 0;

  /**
   * principal views for the scanners
   * @param utilService
   * @param scannerService
   * @param activatedRoute
   * @param session
   * @param router
   * @param modal
   * @param impulseService
   * @param dialog
   */
  constructor(
    public utilService: UtilService,
    public scannerService: ScannerService,
    private configService: AccountConfigService,
    private activatedRoute: ActivatedRoute,
    public session: SessionService,
    private router: Router,
    private clientService: ClientService,
    private modal: ModalService,
    public impulseService: ImpulseService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.uuid_client = this.activatedRoute.snapshot.paramMap.get('uuid_client');
    this.id_client = this.activatedRoute.snapshot.paramMap.get('id');
    this.getUserInfo();
    /*) if (this.id_client) {
      this.getUserInfo();
    }*/

    this.getScannerType();
    this.loadTable();
  }

  /*
   * load principal table
   **/
  loadTable(): void {
    this.scannerService.list(this.type, this.uuid_client, (response) => {
      this.planDetail = response.restrictions.detail;
      switch (this.type) {
        case 'one_shot':
          this.limitScanners = response.restrictions.detail.limit_credits;
          this.isOneShot = true;
          break;
        case 'monitor':
          this.limitScanners =
            response.restrictions.detail.total_monitor_licenses;
          this.isMonitor = true;
          break;
        case 'transform':
          this.limitScanners =
            response.restrictions.detail.total_transforma_licenses;
          this.isTransform = true;
          break;
        default:
          this.limitScanners = 0;
          break;
      }
      this.table = response;

      this.occupieds = 0;
      this.table?.records?.forEach((scanner) => {
        if (scanner.status !== 'deleted' && scanner.status !== 'fixed') {
          this.occupieds++;
        }
      });
      this.filteredScanner = this.table?.records;
    });
  }

  /**
   *  change paginator
   * @param pageOfItems
   */
  onChangePage(pageOfItems: Array<any>): void {
    this.copyArray = pageOfItems;
  }

  /**
   * search items in table
   * @param value
   */
  searchResult(value: any): void {
    this.filteredScanner = this.table.records.filter((u) =>
      u.name.toLowerCase().includes(value.name.toLowerCase())
    );
  }

  /**
   * Order table Items
   * @param colName
   */
  sortFunction(colName: string): void {
    const result = this.utilService.sortTable(
      colName,
      true,
      this.filteredScanner,
      this.sortDirection
    );
    this.filteredScanner = result.array;
    this.sortDirection = result.direction;
    this.utilService.changeSelectedPageToFirst(this.paginatorClass);
  }
  /**
   * clean Filters
   */
  clean(): void {
    this.filter = {
      name: '',
    };
    this.filteredScanner = this.table.records;
  }

  /**
   * create a new scanner
   */
  newItem() {
    if (this.session.getAccessConfig().ADMIN) {
      this.router.navigate([
        'admin/module/scanner/' +
          this.type +
          '/' +
          this.uuid_client +
          '/' +
          this.id_client,
      ]);
    } else if (this.session.getAccessConfig().CLIENT) {
      this.router.navigate(['client/module/scanner/' + this.type]);
    }
  }

  /**
   * update a new scanner
   * @param uuid_scanner
   */
  editItem(uuid_scanner: string) {
    if (this.session.getAccessConfig().ADMIN) {
      this.router.navigate([
        'admin/module/scanner/' +
          this.type +
          '/' +
          this.uuid_client +
          '/' +
          uuid_scanner +
          '/' +
          this.id_client +
          '/view',
      ]);
    } else if (this.session.getAccessConfig().CLIENT) {
      this.router.navigate([
        'client/module/scanner/' + this.type + '/' + uuid_scanner + '/view',
      ]);
    }
  }

  /**
   * go to impulse (for transform scanner)
   * @param uuid_scanner
   */
  goImpulse(uuid_scanner: string) {
    if (this.session.getAccessConfig().ADMIN) {
      this.router.navigate(['admin/module/impulse' + '/' + uuid_scanner]);
    } else if (this.session.getAccessConfig().CLIENT) {
      this.router.navigate(['client/module/impulse' + '/' + uuid_scanner]);
    }
  }
  /**
   * Go to client view
   */
  goClient() {
    if (this.session.getAccessConfig().ADMIN) {
      this.router.navigate(['admin/module/clients']);
    }
  }

  /**
   * clone scanner data from configuration
   * @param uuid_scanner
   */
  clone(uuid_scanner: string) {
    if (this.session.getAccessConfig().ADMIN) {
      this.router.navigate([
        'admin/module/scanner/' +
          this.type +
          '/' +
          this.uuid_client +
          '/' +
          uuid_scanner +
          '/' +
          this.id_client +
          '/clone',
      ]);
    }
  }

  /**
   * delete scanner (logical)
   * @param uuid_scanner
   */
  delete(uuid_scanner: string) {
    if (this.session.getAccessConfig().ADMIN) {
      this.modal.openConfirmation({
        message: 'message.confirmation.scanner.delete',
        onConfirm: () => {
          this.scannerService.delete(uuid_scanner, () => {
            this.loadTable();
          });
        },
      });
    }
  }

  /**
   * pause or resumen scanner execution
   * @param uuid_scanner
   * @param status
   */
  pauseResume(uuid_scanner: string, status: string) {
    if (this.session.getAccessConfig().ADMIN) {
      this.modal.openConfirmation({
        message:
          status === 'paused'
            ? 'message.confirmation.scanner.resume'
            : 'message.confirmation.scanner.pause',
        onConfirm: () => {
          this.scannerService.pauseResume(uuid_scanner, () => {
            this.loadTable();
          });
        },
      });
    }
  }

  /**
   * get scanner type
   * @private
   */
  private getScannerType(): void {
    const module_scanner = '/module/scanner/global_view/';
    if (window.location.href.includes(module_scanner)) {
      this.type = window.location.href
        .split(module_scanner)[1]
        .split('/')[0]
        .split('?')[0];
    }
    //console.log('type', this.type);
  }

  /**
   * split kewyword to show on the view as an array
   * @param word
   */
  splitKeywords(word) {
    let usingSplit = [];
    if (word.length > 0) {
      usingSplit = word.split(',');
      return usingSplit;

      /* return ' ' + word.split('/').map((text) => {
         return {
           message: text.trim()
         };
       });*/
    }
  }

  /**
   * format date
   * @param dt
   */
  formatDate(dt) {
    return moment(dt).utcOffset(0, true);
  }

  /**
   * Open a modal to add a comment to the scanner
   * @param scannerInfo
   */
  commentScan(scannerInfo: any) {
    // console.log('scannerInfo', scannerInfo);
    const dialogRef = this.dialog.open(CommentScannerComponent, {
      closeOnNavigation: true,
      disableClose: true,
      width: '90%',
      data: {
        info: scannerInfo,
      },
    });
    dialogRef.afterClosed().subscribe((result) => {
      this.loadTable();
    });
  }

  /**
   * Open a modal to add a comment to the scanner
   * @param scannerInfo
   */
  notifications(scannerInfo: any) {
    //console.log('notifications', scannerInfo);
    const dialogRef = this.dialog.open(AlertScannersComponent, {
      closeOnNavigation: true,
      disableClose: true,
      width: '50%',
      data: {
        info: scannerInfo,
        plan: this.planDetail,
      },
    });
    dialogRef.afterClosed().subscribe((result) => {
      this.loadTable();
    });
  }
  /**
   * gets informtion of user configuration about send email of a client.
   */
  getUserInfo() {
    if (this.session.getAccessConfig().ADMIN) {
      this.clientService.get(this.id_client, (response) => {
        this.clientAlerts = response.send_email;
        this.clientData = response;
      });
    } else {
      this.configService.getInfo((response) => {
        this.clientAlerts = response.send_email;
        this.clientData = response;
      });
    }
  }
  /**
   * Check if alert icon is available
   * @param status
   * @returns
   */
  checkAlert(status) {
    if (this.type === 'one_shot') {
      return false;
    } else {
      if (this.clientAlerts && status?.ACTIVE) {
        return true;
      } else {
        return false;
      }
    }
  }
  /**
   * Check if alert icon is disasble
   */
  checkAlertDisabled(status) {
    if (this.type === 'one_shot') {
      return false;
    } else {
      if (this.clientAlerts && !status?.ACTIVE) {
        return true;
      }
      if (!this.clientAlerts && status?.ACTIVE) {
        return true;
      }
      if (!this.clientAlerts && !status?.ACTIVE) {
        return true;
      } else {
        return false;
      }
    }
  }
}
