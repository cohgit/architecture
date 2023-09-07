import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {ModalConfig} from '../../helpers/modal.service';

@Component({
  selector: 'app-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.css']
})
export class ModalComponent {
  config: ModalConfig;

  /**
   * modal for modal services
   * @param _config
   * @param dialogRef
   */
  constructor(@Inject(MAT_DIALOG_DATA) private _config: any,
              private dialogRef: MatDialogRef<ModalComponent>) {
    this.config = _config;
  }

  /**
   * on confirm pass data to the service
   * @param data
   */
  onConfirmClick(data: any) {
    if (this.config.onConfirm) {
      this.config.onConfirm(data);
    }

    this.dialogRef.close(true);
  }
}
