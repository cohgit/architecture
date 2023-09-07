import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, NgForm } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { TranslateService } from '@ngx-translate/core';
import { SessionService } from 'src/app/helpers/session.service';
import { TranslateHelperService } from 'src/app/helpers/translate-helper.service';
import { UtilService } from 'src/app/helpers/util.service';
import { CommonService } from 'src/app/services/common.service';
import { ScannerCommentsService } from 'src/app/services/scanner-comments.service';
import { ScannerService } from 'src/app/services/scanner.service';
import { AlertService } from 'src/app/services/alert.service';

@Component({
  selector: 'app-alert-scanners',
  templateUrl: './alert-scanners.component.html',
  styleUrls: ['./alert-scanners.component.css'],
})
export class AlertScannersComponent implements OnInit {
  alert = {
    id_scanner: '',
    periocity: 'daily',
    page_object: 'monitor',
    new_content_detected: false,
    negative_content_detected: false,
    displaced_url: false,
    matching_tracking_word: false,
    matching_keyword: false,
    id_tracking: '',
    tracking_word: '',
    id_keyword: '',
    keyword: '',
    status: true,
  };
  all_Keyword = [];
  all_Tracking = [];
  periot = [];
  objectGo = [];
  info: any;
  plan: any;
  isNew = false;
  constructor(
    private formBuilder: FormBuilder,
    private commonService: CommonService,
    @Inject(MAT_DIALOG_DATA) private data: any,
    private dialogRef: MatDialogRef<AlertScannersComponent>,
    public translate: TranslateService,
    public translateHelper: TranslateHelperService,
    public utilService: UtilService,
    private alertService: AlertService
  ) {
    this.info = data.info;
    this.plan = data.plan;
  }

  ngOnInit(): void {
    // console.log('this.info', this.info);
    this.periot = ['daily', 'weekly'];
    this.objectGo = ['monitor', 'target'];
    this.loadAlarm();
  }

  /**
   * set default alert value
   */
  defaultAlert() {
    this.alert = {
      id_scanner: '',
      periocity: 'daily',
      page_object: 'monitor',
      new_content_detected: false,
      negative_content_detected: false,
      displaced_url: false,
      matching_tracking_word: false,
      matching_keyword: false,
      id_tracking: '',
      tracking_word: '',
      id_keyword: '',
      keyword: '',
      status: true,
    };
  }
  /**
   * load alarm info and load selects
   */
  loadAlarm() {
    let temp_kw = [];
    let temp_tw = [];
    this.alertService.get(this.info.id, (response) => {
      console.log('response', response);

      if (response.id) {
        this.alert = response;
        if (response.id_keyword) {
          temp_kw = this.utilService.splitArray(response.id_keyword);
          this.info.keywords.forEach((kw) => {
            temp_kw.forEach((idKW) => {
              if (+idKW === kw.id) {
                this.all_Keyword.push(kw);
              }
            });
          });
        }
        if (response.id_tracking) {
          temp_tw = this.utilService.splitArray(response.id_tracking);
          this.info.justTrackingWords.forEach((tw) => {
            temp_tw.forEach((idTW) => {
              if (+idTW === tw.id) {
                this.all_Tracking.push(tw);
              }
            });
          });
        }
      } else {
        this.isNew = true;
        this.defaultAlert();
      }
    });
  }

  /**
   * validate form and save / update data
   * @param al
   */
  onConfirmClick(al: NgForm) {
    console.log('alert', al);
    let id_alert_tracking_word = [];
    let alert_tw = [];
    let id_keyword = [];
    let alert_kw = [];
    this.alert.id_scanner = this.info.id;
    // It usses to assig de value in id or word of the tacking word, if not checked cleans element
    if (this.alert.matching_tracking_word) {
      if (this.all_Tracking.length !== 0) {
        this.all_Tracking.forEach((element) => {
          id_alert_tracking_word.push(element.id);
          alert_tw.push(element.word);
        });
        this.alert.id_tracking = id_alert_tracking_word.toString();
        this.alert.tracking_word = alert_tw.toString();
      }
    } else {
      this.alert.id_tracking = '';
      this.alert.tracking_word = '';
    }

    if (this.alert.matching_keyword) {
      if (this.all_Keyword.length !== 0) {
        this.all_Keyword.forEach((element) => {
          id_keyword.push(element.id);
          alert_kw.push(element.word);
        });
      }
      this.alert.keyword = alert_kw.toString();
      this.alert.id_keyword = id_keyword.toString();
    } else {
      this.alert.keyword = '';
      this.alert.id_keyword = '';
    }

    // Check if nothing is checked
    if (
      !this.alert.new_content_detected &&
      !this.alert.negative_content_detected &&
      !this.alert.displaced_url &&
      !this.alert.matching_tracking_word &&
      !this.alert.matching_keyword
    ) {
      this.utilService.showNotification('error.alert_empty', 'danger');
    } else {
      if (
        (this.alert.matching_tracking_word &&
          this.alert.tracking_word === '') ||
        (this.alert.matching_keyword && this.alert.keyword === '')
      ) {
        this.utilService.showNotification('error.alert_empty_check', 'danger');
      } else {
        const data = Object.assign(this.alert, al.value);
        delete data['all_Keyword'];
        delete data['all_Tracking'];
        console.log('data', data);
        if (this.isNew) {
          this.alertService.save(data, (response) => {
            this.dialogRef.close(true);
          });
        } else {
          this.alertService.update(data, (response) => {
            // this.dialogRef.close(true);
          });
        }
      }
    }
  }
}
