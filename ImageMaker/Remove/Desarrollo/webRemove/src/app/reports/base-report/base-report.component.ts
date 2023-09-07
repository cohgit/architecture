import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {TranslateService} from "@ngx-translate/core";
import {UtilService} from "../../helpers/util.service";

@Component({
  selector: 'app-base-report',
  templateUrl: './base-report.component.html',
  styleUrls: ['./base-report.component.css']
})
export class BaseReportComponent implements OnInit {
  tittle: string;
  info: any;
  constructor(@Inject(MAT_DIALOG_DATA) private data: any,
              private dialogRef: MatDialogRef<BaseReportComponent>,
              public translate: TranslateService, private utils: UtilService) {
    this.info = data;
  }

  ngOnInit(): void {
    if (this.info.type.scan){
      this.checkTitle(this.info.scan.type);
    }
  }
  checkTitle(data){
    this.tittle = this.utils.checkTitleScanner(data);
  }

  download(name) {
    this.utils.donwloadPDF(name);
    this.dialogRef.close();
  }
}
