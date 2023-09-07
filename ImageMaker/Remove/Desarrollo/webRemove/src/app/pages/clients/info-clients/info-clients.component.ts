import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { CommonService } from '../../../services/common.service';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { TranslateService } from '@ngx-translate/core';
import { TranslateHelperService } from '../../../helpers/translate-helper.service';
import { ClientService } from '../../../services/client.service';

@Component({
  selector: 'app-info-clients',
  templateUrl: './info-clients.component.html',
  styleUrls: ['./info-clients.component.css'],
})
export class InfoClientsComponent implements OnInit {

  isNuevo = false;
  isEditable = false;
  info: any = {};
  language: string;
  infoHistory: any;

  /**
   * show clients information
   * @param formBuilder
   * @param commonService
   * @param data
   * @param dialogRef
   * @param translate
   * @param clientService
   * @param translateHelper
   */
  constructor(
    private formBuilder: FormBuilder,
    private commonService: CommonService,
    @Inject(MAT_DIALOG_DATA) private data: any,
    private dialogRef: MatDialogRef<InfoClientsComponent>,
    public translate: TranslateService,
    public clientService: ClientService,
    public translateHelper: TranslateHelperService
  ) {
    this.isNuevo = data.nuevo;
    this.isEditable = data.editable;
    this.info = data.info;
  }

  ngOnInit(): void {
    this.checkHistory(this.info.uuid);
  }

  /**
   * open a modal with client activity
   * @param uuid
   */
  checkHistory(uuid: any): void {
    this.clientService.listAudit(uuid, (response) => {
      this.infoHistory = response;
    });
  }
  closeModal() {
    this.dialogRef.close();
  }
}
