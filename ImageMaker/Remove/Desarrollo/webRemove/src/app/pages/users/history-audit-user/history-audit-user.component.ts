import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {TranslateService} from "@ngx-translate/core";
import {TranslateHelperService} from "../../../helpers/translate-helper.service";
import {UtilService} from "../../../helpers/util.service";

@Component({
  selector: 'app-history-audit-user',
  templateUrl: './history-audit-user.component.html',
  styleUrls: ['./history-audit-user.component.css']
})
export class HistoryAuditUserComponent implements OnInit {
  info: any;
  copyArray: Array<any>;

  /**
   * show user history
   * @param formBuilder
   * @param data
   * @param dialogRef
   * @param translate
   * @param translateHelper
   * @param utilService
   */
  constructor(private formBuilder: FormBuilder,
              @Inject(MAT_DIALOG_DATA) private data: any,
              private dialogRef: MatDialogRef<HistoryAuditUserComponent>,
              public translate: TranslateService,
              public translateHelper: TranslateHelperService, public utilService: UtilService) {


    this.info = data.info;
  }

  ngOnInit(): void {
  }
  /**
   *   change paginator
   * @param pageOfItems
   */
  onChangePage(pageOfItems: Array<any>): void {
    this.copyArray = pageOfItems;
  }
}
