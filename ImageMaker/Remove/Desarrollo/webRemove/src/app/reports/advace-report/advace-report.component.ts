import {AfterViewInit, Component, Inject, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {TranslateService} from "@ngx-translate/core";
import {ScannerFilter} from "../../interfaces/IScannerFilter";
import {ModalService} from "../../helpers/modal.service";
import {FiltersComponent} from 'src/app/shared/filters/filters.component';
import {UtilService} from "../../helpers/util.service";
import {ReportService} from "../../services/report.service";
import {CommonService} from 'src/app/services/common.service';
import * as moment from 'moment';

@Component({
  selector: 'app-advace-report',
  templateUrl: './advace-report.component.html',
  styleUrls: ['./advace-report.component.css']
})
export class AdvaceReportComponent implements AfterViewInit {
  @ViewChild('filterCompReport') filterCompReportM: FiltersComponent;

  info: any;
  email = '';
  isSended = false;

  /**
   *
   * @param data
   * @param reportService
   * @param dialogRef
   * @param commonService
   * @param translate
   * @param modal
   * @param utilService
   */
  constructor(@Inject(MAT_DIALOG_DATA) private data: any, private reportService: ReportService,
              private dialogRef: MatDialogRef<AdvaceReportComponent>, private commonService: CommonService,
              public translate: TranslateService, private modal: ModalService, private utilService: UtilService) {
    this.info = data;

    this.commonService.email(response => {
      this.email = response;
    });
  }

  ngAfterViewInit(): void {
    this.filterCompReportM.refreshFilters(this.info.initialData);
  }

  onConfirmClick(filtersApplieds: any): void {
    this.isSended = true;
    const stringInCode = this.translate.instant('message.confirm_report', {
      emailM: this.email
    });

    if (this.email !== '' && this.utilService.validateEmail(this.email)) {
      this.modal.openConfirmation({
        message: stringInCode,
        onConfirm: () => {
         // console.log('filtersApplieds', filtersApplieds);
          this.reportService.generateScannerReport(this.componseRequest(filtersApplieds));

          this.dialogRef.close(true);
        },
      });
    }
  }

  setFilterDefaultValues() {
    this.info.filters = new ScannerFilter();
    this.info.filters.pages = [];

    let endDate = this.info.initialData?.results[0]?.query_date ? moment(this.info.initialData?.results[0]?.query_date) : moment();
    let initDate = this.info.initialData?.creation_date ? moment(this.info.initialData?.creation_date) : moment();

    this.info.filters.init_date = initDate.toDate();
    this.info.filters.end_date = endDate.toDate();
  }

  clearFilters(): void {
    this.setFilterDefaultValues();
  }

  componseRequest(filtersApplieds: any): any {
    let params: any = {};

    params.email = this.email;
    params.uuid_scanner = this.info.initialData.uuid;

    if (filtersApplieds.init_date) {
      params.init_date = moment(filtersApplieds.init_date).format('DD-MM-YYYY');
    }
    if (filtersApplieds.end_date) {
      params.end_date = moment(filtersApplieds.end_date).format('DD-MM-YYYY');
    }

    params.isNew = filtersApplieds.isNew;
    params.pages = filtersApplieds.pages;

    if (filtersApplieds?.feelings?.length > 0) {
      params.feelingsNames = [];
      params.feelings = [];

      filtersApplieds.feelings.forEach(feel => {
        params.feelings.push(feel.tag);
        params.feelingsNames.push(this.translate.instant('feeling.' + feel.tag));
      });
    }

    if (filtersApplieds?.keywords) {
      params.keywords = [];

      filtersApplieds.keywords.forEach(kw => {
        params.keywords.push(kw.word);
      });
    }

    if (filtersApplieds.search_types) {
      params.section = [];
      params.sectionName = [];

      filtersApplieds.search_types.forEach(st => {
        params.section.push(st);
        params.sectionName.push(this.translate.instant('list.scanner.type.' + st));
      });
    }

    if (filtersApplieds?.tracking_words?.length > 0) {
      params.trackingWords = [];

      filtersApplieds.tracking_words.forEach(tw => {
        params.trackingWords.push(tw.word);
      });
    }
    if (filtersApplieds?.urls?.length > 0) {
      params.urls = [];

      filtersApplieds.urls.forEach(url => {
        params.urls.push(url.word);
      });
    }
    if (filtersApplieds?.countries) {
      params.countries = [];

      filtersApplieds.countries.forEach(country => {
        params.countries.push(country.name);
      });
    }
    //console.log('params', params);
    return params;
  }
}

