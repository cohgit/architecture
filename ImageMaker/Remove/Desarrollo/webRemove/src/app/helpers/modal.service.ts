import {Injectable} from '@angular/core';

import {MatDialog} from '@angular/material/dialog';
import {ModalComponent} from "../shared/modal/modal.component";


@Injectable({
  providedIn: 'root'
})
export class ModalService {
  /**
   * Service that can be used throughout the system to create confirmation modals (two options)
   * @param dialog
   */
  constructor(private dialog: MatDialog) {
  }

  /**
   * reusable component for all confirmation modals
   * @param _config
   */
  openConfirmation(_config?: ModalConfig) {
    const config = this.fitConfig(DEFAULT_CONFIG_CONFIRM, _config);

    const dialogRef = this.dialog.open(ModalComponent, {
      closeOnNavigation: true,
      disableClose: true,
      data: this.fitConfig(DEFAULT_CONFIG_CONFIRM, _config)
    });

    dialogRef.afterClosed().subscribe(result => {
      if (config.afterClosed) {
        config.afterClosed(result);
      }
    });
  }

  openNotification(_config?: ModalConfig) {
    const config = this.fitConfig(DEFAULT_CONFIG_NOTIFICATION, _config);

    const dialogRef = this.dialog.open(ModalComponent, {
      closeOnNavigation: true,
      disableClose: true,
      data: config
    });

    dialogRef.afterClosed().subscribe(result => {
      if (config.afterClosed) {
        config.afterClosed(result);
      }
    });
  }

  /**
   * Allows you to configure the default fields of the modal configuration
   * @param def
   * @param config
   * @private
   */
  private fitConfig(def: ModalConfig, config?: ModalConfig): ModalConfig {
    if (config) {
      return Object.assign(def, config);
    } else {
      return def;
    }
  }
}

export class ModalConfig {
  isConfirm?: boolean;
  isNotification?: boolean;
  title?: string;
  message?: string;
  confirmButtonText?: string;
  cancelButtonText?: string;
  onConfirm?: Function;
  afterClosed?: Function;
}

const DEFAULT_CONFIG_CONFIRM = {
  isConfirm: true,
  isNotification: false,
  title: 'title.modal.confirmation',
  message: 'message.question.sure.confirmation',
  confirmButtonText: 'button.confirm',
  cancelButtonText: 'button.cancel',
  onConfirm: () => {
  },
  afterClosed: () => {
  }
};
const DEFAULT_CONFIG_NOTIFICATION = {
  isConfirm: false,
  isNotification: true,
  title: 'title.modal.notification.advise',
  message: '',
  closeButtonText: 'button.close',
  afterClosed: () => {
  }
};
