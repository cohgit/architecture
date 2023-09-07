import {Component, OnInit} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {ModalService} from '../../helpers/modal.service';
import {UtilService} from '../../helpers/util.service';
import {TranslateHelperService} from '../../helpers/translate-helper.service';
import {CommonService} from '../../services/common.service';
import {ImpulseService} from '../../services/impulse.service';
import {NewEditImpulseComponent} from './new-edit-impulse/new-edit-impulse.component';
import {DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE} from '@angular/material/core';
import {MAT_MOMENT_DATE_ADAPTER_OPTIONS, MomentDateAdapter} from '@angular/material-moment-adapter';
import * as moment from 'moment';
import {Moment} from 'moment';
import {MatDatepicker} from '@angular/material/datepicker';
import {ActivatedRoute} from '@angular/router';
import {SelectDateComponent} from './select-date/select-date.component';
import {FinalURLComponent} from './final-url/final-url.component';
import {ManageNewsComponent} from './manage-news/manage-news.component';
import {TranslateService} from '@ngx-translate/core';
import {SessionService} from 'src/app/helpers/session.service';
import {ImpulseVariableService} from 'src/app/services/impulse-variable.service';
import {ApproveImpulseComponent} from "./approve-impulse/approve-impulse.component";

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
  selector: 'app-impulse',
  templateUrl: './impulse.component.html',
  styleUrls: ['./impulse.component.css'],
  providers: [
    {
      provide: DateAdapter,
      useClass: MomentDateAdapter,
      deps: [MAT_DATE_LOCALE, MAT_MOMENT_DATE_ADAPTER_OPTIONS]
    },
    {provide: MAT_DATE_FORMATS, useValue: MONTH_YEAR_FORMAT}
  ]
})
export class ImpulseComponent implements OnInit {
  uuid_scanner: string;

  info: any = {impulses: []};
  noneValue = '';
  impulsesFiltered: Array<any>;
  allStates = [];
  allTypes = [];
  today = new Date();
  minDate = new Date(new Date().setFullYear(new Date().getFullYear() - 2));
  private accessConfig = null;
  paginatorClass = 'paginator';
  /* objeto a enviar al filtro */
  filter = {
    title: '',
    type: '',
    state: '',
    author: '',
    month: moment(),
  };

  /* dirección ascendente o descendente */
  sortDirection = false;

  /**
   * manages all the impulse information
   * @param dialog
   * @param utilService
   * @param impulseService
   * @param translateHelper
   * @param translate
   * @param session
   * @param commonService
   * @param activatedRoute
   * @param variableService
   * @param modal
   */
  constructor(private dialog: MatDialog, public utilService: UtilService,
              public impulseService: ImpulseService, public translateHelper: TranslateHelperService,
              public translate: TranslateService, private session: SessionService,
              private commonService: CommonService, private activatedRoute: ActivatedRoute,
              public variableService: ImpulseVariableService, private modal: ModalService) {
    this.accessConfig = this.session.getAccessConfig();
  }

  ngOnInit(): void {
    this.loadTable();
    this.loadCommons();
    this.noneValue = this.translate.instant('label.none');

  }

  /*
load commons services to fill select inputs
* */
  loadCommons(): void {
    this.commonService.listImpulseStatus(response => this.allStates = response);
    this.commonService.listImpulseTypes(response => this.allTypes = response);
  }

  /*
  load principal table
   * */
  loadTable(): void {
    this.uuid_scanner = this.activatedRoute.snapshot.paramMap.get('uuid_scanner');
    this.impulseService.list(this.uuid_scanner, response => this.info = response);

  }

  /**
   *   function to open modal
   * @param isNuevo
   * @param isEditable
   * @param impulse
   * @param componente
   * @param large
   */
  openModal(isNuevo: boolean, isEditable: boolean, impulse: any, componente, large): void {
    const dialogRef = this.dialog.open(componente, {
      closeOnNavigation: true,
      disableClose: true,
      width: large,
      data: {
        nuevo: isNuevo,
        editable: isEditable,
        info: impulse,
        scanner: this.info.scanner,
        impulses: this.info.impulses,
      }
    });
    dialogRef.afterClosed().subscribe(() => {
      this.loadTable();
    });
  }

  /**
   *  Open a model to edit an Item
   * @param impulse
   */
  editItem(impulse: any): void {
    impulse.content = impulse.content ? impulse.content : {};
    this.openModal(false, true, impulse, NewEditImpulseComponent, '100%');
  }

  /**
   * Open a model to create a new Item
   */
  newItem(): void {
    this.openModal(true, false, {
      uuidScanner: this.uuid_scanner,
      content: {},
      editableSolitude: true,
      editableContent: true
    }, NewEditImpulseComponent, '80%');
  }

  /**
   *   change paginator
   * @param pageOfItems
   */
  onChangePage(pageOfItems: Array<any>): void {
    this.impulsesFiltered = pageOfItems;
  }

  /**
   *   search items in table with the filters values
   * @param filters
   */
  searchResult(filters: any): void {
    this.impulsesFiltered = this.info.impulses.filter(impulse => {
      return this.filterTittle(filters.title, impulse) && this.filterStatus(filters.state, impulse) &&
        this.filterType(filters.type, impulse) && this.filterEstimatedDate(filters.month, impulse)
    });
  }

  /**
   * filter an array by tittle
   * @param param
   * @param impulse
   * @private
   */
  private filterTittle(param: string, impulse: any): boolean {
    return param ? impulse?.content?.title?.toLowerCase().includes(param.toLowerCase()) : true;
  }

  /**
   * filter an array by status
   * @param param
   * @param impulse
   * @private
   */
  private filterStatus(param: string, impulse: any): boolean {
    return param ? impulse?.statusObj?.tag === param : true;
  }

  /**
   * filter an array by type
   * @param param
   * @param impulse
   * @private
   */
  private filterType(param: string, impulse: any): boolean {
    return param ? impulse?.type === param : true;
  }

  /**
   * filter an array by estimated date
   * @param param
   * @param impulse
   * @private
   */
  private filterEstimatedDate(param: any, impulse: any): boolean {
   // console.log(param, impulse)
    return param ? moment(impulse?.estimated_publish, 'YYYY-MM-DD').format("MM/YYYY") === moment(new Date(param)).format("MM/YYYY") : true;
  }

  /**
   * Order table Items
   * @param colName
   */
  sortFunction(colName: string): void {
    const result = this.utilService.sortTable(colName, true, this.info?.impulses, this.sortDirection);
    this.info.impulses = result.array;
    this.sortDirection = result.direction;
    this.utilService.changeSelectedPageToFirst(this.paginatorClass);
  }

  /**
   * Event change year in datepicker
   * @param normalizedYear
   */
  chosenYearHandler(normalizedYear: Moment): void {
    normalizedYear.month(this.filter.month.month());
    this.filter.month = normalizedYear;
  }

  /**
   * Event change month in datepicker
   * @param normalizedMonth
   * @param datepicker
   */
  chosenMonthHandler(normalizedMonth: Moment, datepicker: MatDatepicker<Moment>): void {
    this.filter.month = normalizedMonth;
    datepicker.close();
    this.searchResult(this.filter);
  }

  /**
   * open a modal to set estimate date
   * @param impulse
   */
  setEstimatedDate(impulse: any): void {
    this.openModal(false, true, {
      data: impulse,
      plan: this.info.scanner.restrictions.detail,
      list: this.info.impulses
    }, SelectDateComponent, '');
  }

  /**
   * open a modal to set final URL
   * @param impulse
   */
  setURL(impulse: any): void {
    this.openModal(false, true, impulse, FinalURLComponent, '');
  }

  /**
   * Open a modal to manage news
   * @param impulse
   */
  newsDetail(impulse: any): void {
    this.openModal(false, true, impulse, ManageNewsComponent, '80%');
  }

  /**
   * clean Filters
   */
  clean(): void {
    this.filter = {
      title: '',
      type: '',
      state: '',
      author: '',
      month: moment(),
    };
    this.impulsesFiltered = this.info.impulses;
  }

  /**
   * change status to aprove
   * @param impulse
   */
  aproveImpulse(impulse: any) {
    this.modal.openConfirmation({
      message: 'message.confirmation.impulse.approved',
      onConfirm: () => {
        if (impulse.content) {
          this.impulseService.update(impulse.info, () => {
            this.impulseService.askApproval(impulse.content.id, impulse.comments, () => {

            });
          });
        } else {
          this.utilService.showNotification('error.required_fields', 'danger');
        }
      },
    });


  }

  /**
   * open a modal to approve or not an impulse
   * @param impulse
   */
  evaluateImpulse(impulse: any) {
    this.openModal(false, true, impulse, ApproveImpulseComponent, '');
  }
}
