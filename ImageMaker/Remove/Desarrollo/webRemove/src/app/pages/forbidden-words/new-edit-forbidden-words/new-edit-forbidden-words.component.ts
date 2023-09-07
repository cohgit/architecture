import {Component, Inject, OnInit} from '@angular/core';
import {TrackingWords} from "../../../interfaces/ITrackingWords";
import {FormBuilder} from "@angular/forms";
import {CommonService} from "../../../services/common.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {TranslateService} from "@ngx-translate/core";
import {UtilService} from "../../../helpers/util.service";
import {ForbiddenWordService} from "../../../services/forbidden-word.service";

@Component({
  selector: 'app-new-edit-forbidden-words',
  templateUrl: './new-edit-forbidden-words.component.html',
  styleUrls: ['./new-edit-forbidden-words.component.css']
})
export class NewEditForbiddenWordsComponent implements OnInit {
  isNuevo = false;
  isEditable = false;
  info: any = {};
  allFeelings: any;
  allWords: any;
  allTracking: TrackingWords[] = [];
  showTable = false;
  copyArray: Array<any>;
  massive = false;

  /**
   * store or update an item
   * @param formBuilder
   * @param commonService
   * @param data
   * @param forbiddenWordServices
   * @param dialogRef
   * @param translate
   * @param utilService
   */
  constructor(private formBuilder: FormBuilder,
              private commonService: CommonService,
              @Inject(MAT_DIALOG_DATA) private data: any,
              public forbiddenWordServices: ForbiddenWordService,
              private dialogRef: MatDialogRef<NewEditForbiddenWordsComponent>,
              public translate: TranslateService, public utilService: UtilService) {
    this.isNuevo = data.nuevo;
    this.isEditable = data.editable;
    this.info = data.info;
  }

  ngOnInit(): void {

  }


  /**
   * call services update or save as appropriate
   * @param form
   */
  onConfirmClick(form: any) {
    if (form.valid) {
      const data = Object.assign(this.info, form.value);
      if (this.isNuevo) {
        this.forbiddenWordServices.save(data, response => {
          this.info = response;
          this.isNuevo = false;
          this.dialogRef.close(true);
        });
      } else {
        this.forbiddenWordServices.update(data, response => {
          this.info = response;
          this.dialogRef.close(true);
        });
      }
    }

  }


}
