import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, NgForm} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {TranslateService} from '@ngx-translate/core';
import {DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE} from '@angular/material/core';
import {MAT_MOMENT_DATE_ADAPTER_OPTIONS, MomentDateAdapter} from '@angular/material-moment-adapter';
import * as moment from 'moment';
import {Moment} from 'moment';
import {MatDatepicker} from '@angular/material/datepicker';
import {ImpulseService} from '../../../services/impulse.service';

export const MONTH_YEAR_FORMAT = {
  parse: {
    dateInput: 'MM/YYYY',
  },
  display: {
    dateInput: 'MM/YYYY',
    monthYearLabel: 'MMM YYYY',
    dateA11yLabel: 'LL',
    monthYearA11yLabel: 'MMMM YYYY',
  },
};

@Component({
  selector: 'app-select-date',
  templateUrl: './select-date.component.html',
  styleUrls: ['./select-date.component.css'],
  providers: [
    {
      provide: DateAdapter,
      useClass: MomentDateAdapter,
      deps: [MAT_DATE_LOCALE, MAT_MOMENT_DATE_ADAPTER_OPTIONS]
    },
    {provide: MAT_DATE_FORMATS, useValue: MONTH_YEAR_FORMAT}
  ]
})


export class SelectDateComponent implements OnInit {

  today = new Date();
  info: any;
  plan: any;
  otherImpulses: any[] = [];
  monthYear = moment();
  message: string;
  available: number;

  /**
   * pick the month and year when iis going to be publish
   * @param formBuilder
   * @param impulseService
   * @param data
   * @param dialogRef
   * @param translate
   */
  constructor(private formBuilder: FormBuilder, private impulseService: ImpulseService,
              @Inject(MAT_DIALOG_DATA) private data: any,
              private dialogRef: MatDialogRef<SelectDateComponent>,
              public translate: TranslateService) {
    this.info = data.info.data;
    this.plan = data.info.plan;
    if (data.info.list) {
      data.info.list.forEach(imp => {
        if (imp.id !== data.info.data.id) {
          this.otherImpulses.push(imp);
        }
      });
    }
    this.checkDateScheduled();
  }

  ngOnInit(): void {
  }

  /**
   * update or save the date
   * @param form
   */
  onConfirmClick(form: NgForm) {
    if (form.value) {
      this.info.estimated_publish = this.monthYear;

      this.impulseService.updateEstimatedPublish(this.info, () => {
        this.dialogRef.close(true);
      });

      this.dialogRef.close(true);
    }
  }

  /**
   * Event change year in datepicker
   * @param normalizedYear
   */
  chosenYearHandler(normalizedYear: Moment) {
    normalizedYear.month(this.monthYear.month());
    this.monthYear = normalizedYear;
  }

  /**
   * Event change month in datepicker
   * @param normalizedMonth
   * @param datepicker
   */
  chosenMonthHandler(normalizedMonth: Moment, datepicker: MatDatepicker<Moment>) {
    this.monthYear = normalizedMonth;
    datepicker.close();

    this.checkDateScheduled();
  }

  /**
   * Check if is an available date
   */
  checkDateScheduled() {
    let totalForMonthYear = 0;
    this.otherImpulses.forEach(other => {
      if ((other.real_publish_date && this.sameMonthYear(other.real_publish_date, this.monthYear)) || (other.estimated_publish && this.sameMonthYear(other.estimated_publish, this.monthYear))) {
        totalForMonthYear++;
      }
    });

    this.available = this.plan.max_url_to_impulse - totalForMonthYear;
    this.message = this.available <= 0 ? 'warning.max.monthly.impulses.reached' : 'message.monthly.impulses.availables';
  }

  /**
   * Check if both dates are in the same month and year
   * @param date1
   * @param date2
   * @returns
   */
  sameMonthYear(date1: string, date2: moment.Moment): boolean {
    const _date1 = moment(date1);
    return _date1.month() === date2.month() && _date1.year() === date2.year();
  }
}
