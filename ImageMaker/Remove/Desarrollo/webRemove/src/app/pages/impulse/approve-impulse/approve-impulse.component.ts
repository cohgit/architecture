import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {ImpulseService} from "../../../services/impulse.service";
import {ModalService} from "../../../helpers/modal.service";
import {SessionService} from "../../../helpers/session.service";
import {UtilService} from "../../../helpers/util.service";

@Component({
  selector: 'app-approve-impulse',
  templateUrl: './approve-impulse.component.html',
  styleUrls: ['./approve-impulse.component.css']
})
export class ApproveImpulseComponent implements OnInit {
  aprove: any = null;
  isTrue = true;
  isFalse = false;
  comment = '';
  info: any;

  /**
   * modal to approve a impulse
   * @param data
   * @param session
   * @param impulseService
   * @param modal
   * @param utilService
   * @param dialogRef
   */
  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private session: SessionService,
              private impulseService: ImpulseService, private modal: ModalService, private utilService: UtilService,
              private dialogRef: MatDialogRef<ApproveImpulseComponent>) {
    this.info = data.info;
  }

  ngOnInit(): void {
  }

  /** call to approve  or  reject an impulse
   * @param form
   */
  approve(form: any) {
    this.modal.openConfirmation({
      message: 'message.confirmation.impulse.approved',
      onConfirm: () => {
        this.impulseService.approve(this.data.info.content.id, this.comment, response => {
          response.user = this.session.getUser().name;
          response.profile = 'falta el nombre completo de quien agregó este comentario y su perfil';
          this.dialogRef.close(true);
        });
      },
    });
  }

  /**
   * call a service to  reject an impulse
   * @param form
   */
  reject(form: any) {
    if (this.comment !== '' && this.aprove !== null) {
      this.modal.openConfirmation({
        message: 'message.confirmation.impulse.reject',
        onConfirm: () => {
          this.impulseService.reject(this.data.info.content.id, this.comment, response => {
            response.user = this.session.getUser().name;
            response.profile = 'falta el nombre completo de quien agregó este comentario y su perfil';
            this.dialogRef.close(true);
          });
        },
      });

    } else {
      this.utilService.showNotification('error.impulse.comment', 'danger');
    }
  }
}
