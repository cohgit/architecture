import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {UtilService} from "../../../../helpers/util.service";
import {CommonService} from "../../../../services/common.service";
import {TranslateHelperService} from "../../../../helpers/translate-helper.service";

@Component({
  selector: 'app-confirm-scanner',
  templateUrl: './confirm-scanner.component.html',
  styleUrls: ['./confirm-scanner.component.css']
})
export class ConfirmScannerComponent implements OnInit {
info: any;
message: any;
tittle: any;
  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public utilService: UtilService,
              private dialogRef: MatDialogRef<ConfirmScannerComponent>, private commonService: CommonService,
              public translateHelper: TranslateHelperService) {
   // console.log('recibe: ', this.data);
  }

  ngOnInit(): void {
    this.info = this.data.info;
    this.tittle = this.data.tittle;
    this.message = this.data.message;
  }

  execute(act) {
    const result = {
      action: act,
      close: true
    };
    this.dialogRef.close(result);
  }

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
}
