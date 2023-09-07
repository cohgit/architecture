import { Component, Inject, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { SessionLogService } from '../../../services/session-log.service';
import { SessionService } from '../../../helpers/session.service';
import { UtilService } from '../../../helpers/util.service';
import { ModalService } from '../../../helpers/modal.service';

@Component({
  selector: 'app-confirm-change',
  templateUrl: './confirm-change.component.html',
  styleUrls: ['./confirm-change.component.css'],
})
export class ConfirmChangeComponent implements OnInit {
  info: any = {};
  sidebar: any;
  confirmtoggle = false;
  displayWarningTransform = false;
  displayWarningMonitor = false;
  listMonitorDeactivate = 0;
  listTransformDeactivate = 0;
  alertLicences = false;

  constructor(
    @Inject(MAT_DIALOG_DATA) private data: any,
    private logoutService: SessionLogService,
    public session: SessionService,
    private dialogRef: MatDialogRef<ConfirmChangeComponent>,
    public utilService: UtilService,
    private modal: ModalService
  ) {
    this.info = data.info;
    this.sidebar = this.data.sidenav;
  }

  ngOnInit(): void {
    console.log('this.info', this.info);
    if (this.info.paymentsMethodsAvailables.length !== 0) {
      this.info.paymentMethod = this.info.paymentsMethodsAvailables[0];
      console.log(this.info.paymentMethod);
    }
    this.listTransformDeactivate = this.info.scannersToMigrateTransforma.length;
    this.listMonitorDeactivate = this.info.scannersToMigrateRecurrent.length;
  }

  /**
   * Verifica que no existan más licencias de la que puede aceptar
   * @param form
   */
  onConfirmClick(form: NgForm) {
    this.alertLicences = false;
    let checkTransf = 0;
    let checkMoni = 0;
    console.log(this.info);
    if (form.valid) {
      this.modal.openConfirmation({
        message: 'message.confirm_plan',
        onConfirm: () => {
          checkTransf = this.checktoDelete(
            this.listTransformDeactivate,
            this.info?.newPlan?.total_transforma_licenses
          );
          checkMoni = this.checktoDelete(
            this.listMonitorDeactivate,
            this.info?.newPlan?.total_monitor_licenses
          );

          if (checkTransf <= 0 && checkMoni <= 0) {
            this.dialogRef.close(true);
          } else {
            this.alertLicences = true;
            this.utilService.showNotification(
              'error.changingplans.licences',
              'danger'
            );
          }
        },
      });
    }
  }

  /**
   * Resta el total de licencias de lista (checks) vs la máxima que debe aceptar
   * @param listValue
   * @param defaultValue
   */
  checktoDelete(listValue, defaultValue) {
    let total = 0;
    total = listValue - defaultValue;
    return total;
  }

  /**
   * Logout service
   */
  logout(): void {
    this.logoutService.logout(() => this.session.destroySession(), {
      errorFunction: () => this.session.destroySession(),
    });
  }

  /**obtiene la lista de scanners a desactivar a medida que se cambian los checks en el hijo
   *
   * @param $event
   */
  checkListMonitor($event: any) {
    this.listMonitorDeactivate = $event;
  }

  /**
   * obtiene la lista de scanners a desactivar a medida que se cambian los checks en el hijo
   * @param $event
   */
  checkListTransform($event: any) {
    this.listTransformDeactivate = $event;
  }

  checkWarningtransfrom($event: any) {
    this.displayWarningTransform = false;
    if ($event) {
      this.displayWarningTransform = true;
    }
  }

  checkWarningMonitor($event: any) {
    this.displayWarningMonitor = false;
    if ($event) {
      this.displayWarningMonitor = true;
    }
  }
}
