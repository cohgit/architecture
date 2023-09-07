import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder} from '@angular/forms';
import {CommonService} from '../../../services/common.service';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {TranslateService} from '@ngx-translate/core';
import {TrackingWordService} from '../../../services/tracking-word.service';
import * as XLSX from 'xlsx';
import {UtilService} from '../../../helpers/util.service';
import {ITrackingWords, TrackingWords} from '../../../interfaces/ITrackingWords';

@Component({
  selector: 'app-new-edit-trackingwords',
  templateUrl: './new-edit-trackingwords.component.html',
  styleUrls: ['./new-edit-trackingwords.component.css']
})
export class NewEditTrackingwordsComponent implements OnInit {
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
   * update and save information for tracking words
   * @param formBuilder
   * @param commonService
   * @param data
   * @param trackingwordService
   * @param dialogRef
   * @param translate
   * @param utilService
   */
  constructor(private formBuilder: FormBuilder,
              private commonService: CommonService,
              @Inject(MAT_DIALOG_DATA) private data: any,
              public trackingwordService: TrackingWordService,
              private dialogRef: MatDialogRef<NewEditTrackingwordsComponent>,
              public translate: TranslateService, public utilService: UtilService) {
    this.isNuevo = data.nuevo;
    this.isEditable = data.editable;
    this.info = data.info;
  }

  ngOnInit(): void {
    this.loadCommons();
  }
  /*
load commons services to fill select inputs
* */
  loadCommons(): void{
    this.commonService.listFeelingsIgnoreNotApply(response =>  this.allFeelings = response);
    this.commonService.listTrackingWords(response => this.allWords = response);
  }

  /**
   * call services update or save as appropriate
   * @param form
   */
  onConfirmClick(form: any) {
    if (form.valid) {
      const data = Object.assign(this.info, form.value);
      if (this.isNuevo) {
        if (this.massive) {
          this.trackingwordService.loadFile(this.allTracking, response => {
            this.info = response;
            this.isNuevo = false;
            this.dialogRef.close(true);
          });
        } else {
          this.trackingwordService.save(data, response => {
            this.info = response;
            this.isNuevo = false;
            this.dialogRef.close(true);
          });
        }
      } else {
        this.trackingwordService.update(data, response => {
          this.info = response;
          this.dialogRef.close(true);
        });
      }
    }

  }
  /**
   *
   * @param ev
   * process excelfile
   */
  onFileChange(ev) {
    this.allTracking = [];
    let workBook = null;
    let jsonData = null;
    const reader = new FileReader();
    const file = ev.target.files[0];
    const extension = file.name.split('.').pop();
    if (extension === 'xlsx' || extension === 'csv' || extension === 'xls'){
      reader.onload = (event) => {
        const data = reader.result;
        workBook = XLSX.read(data, { type: 'binary' });
        jsonData = workBook.SheetNames.reduce((initial, name) => {
          const sheet = workBook.Sheets[name];
          initial = XLSX.utils.sheet_to_json(sheet);
          return initial;
        }, {});

        this.validateExcel(jsonData);
      };
      reader.readAsBinaryString(file);
    } else {
      this.utilService.showNotification(this.translate.instant('error.excel.format'), 'danger');
    }
  }
  /**
   *
   * @param ev
   * validate excel: empty file, wrong writting, empty row, no words
   */
  validateExcel(dataExcel){
    const missingInfo = [];
    let feeling: any;
    if (dataExcel.length !== 0) {
      dataExcel.forEach( (d, index) => {
        if (!d.palabra || !d.sentimiento || ((d.sentimiento).toUpperCase() !== 'NEUTRAL' &&
          (d.sentimiento).toUpperCase() !== 'NEGATIVO' && (d.sentimiento).toUpperCase() !== 'POSITIVO')) {
          missingInfo.push(index + 1);
        }
        if ((d.sentimiento).toUpperCase() === 'NEUTRAL') {
          d.sentimiento = 'neutral';
          feeling = {
            NEUTRAL: true,
            tag: d.sentimiento
          }; }
        if ((d.sentimiento).toUpperCase() === 'NEGATIVO') {
          d.sentimiento = 'bad';
          feeling = {
            BAD: true,
            tag: d.sentimiento
          }; }
        if ((d.sentimiento).toUpperCase() === 'POSITIVO') {
          d.sentimiento = 'good';
          feeling = {
            GOOD: true,
            tag: d.sentimiento
          }; }
        this.allTracking.push({
          feeling: d.sentimiento,
          word: d.palabra,
          feelingObj: feeling
        });
      });
    }
    else{
      this.utilService.showNotification(this.translate.instant('error.excel.empty'), 'danger');
    }

    if (missingInfo.length !== 0){
      this.utilService.showNotification(this.translate.instant('error.excel.empty_row') + missingInfo, 'danger');
    }
    else {
      this.showTable = true;
    }
  }
  /**
   *   change paginator
   * @param pageOfItems
   */
  onChangePage(pageOfItems: Array<any>): void {
    this.copyArray = pageOfItems;
  }

}
