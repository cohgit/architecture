import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ModalService } from '../../helpers/modal.service';
import { UtilService } from '../../helpers/util.service';
import { TranslateHelperService } from '../../helpers/translate-helper.service';
import { TrackingWordService } from '../../services/tracking-word.service';
import { NewEditTrackingwordsComponent } from './new-edit-trackingwords/new-edit-trackingwords.component';
import { ITrackingWords } from '../../interfaces/ITrackingWords';
import { CommonService } from '../../services/common.service';

@Component({
  selector: 'app-trackingwords',
  templateUrl: './trackingwords.component.html',
  styleUrls: ['./trackingwords.component.css'],
})
export class TrackingwordsComponent implements OnInit {
  allTracking: ITrackingWords[];
  copyArray: Array<any>;
  allFeelings = [];
  filteredTracking = [];
  /* objeto a enviar al filtro */
  filter = {
    word: '',
    feeling: '',
    active: true,
  };
  sortDirection = false;
  paginatorClass = 'paginator';

  /**
   * manage tracking word information
   * @param dialog
   * @param modalService
   * @param utilService
   * @param trackingwordService
   * @param translateHelper
   * @param commonService
   */
  constructor(
    private dialog: MatDialog,
    private modalService: ModalService,
    private utilService: UtilService,
    public trackingwordService: TrackingWordService,
    public translateHelper: TranslateHelperService,
    private commonService: CommonService
  ) {}

  ngOnInit(): void {
    this.loadTable();
    this.loadCommons();
  }

  /*
load commons services to fill select inputs
* */
  loadCommons(): void {
    this.commonService.listFeelingsIgnoreNotApply(
      (response) => (this.allFeelings = response)
    );
  }

  /*
  load principal table
   * */
  loadTable(): void {
    this.trackingwordService.list((response) => {
      this.allTracking = response;
      this.allTracking.sort((a, b) => a.word.localeCompare(b.word));
      this.filteredTracking = this.allTracking;
    });
  }

  /**
   *   function to open modal
   * @param isNuevo
   * @param isEditable
   * @param trackingword
   * @param componente
   */
  openModal(
    isNuevo: boolean,
    isEditable: boolean,
    trackingword: ITrackingWords,
    componente
  ): void {
    const dialogRef = this.dialog.open(componente, {
      closeOnNavigation: true,
      disableClose: true,
      width: '70%',
      data: {
        nuevo: isNuevo,
        editable: isEditable,
        info: trackingword,
      },
    });
    dialogRef.afterClosed().subscribe((result) => {
      this.loadTable();
    });
  }

  /**
   *  Open a model to edit an Item
   * @param tw
   */
  editItem(tw: any): void {
    this.openModal(
      false,
      this.trackingwordService.permissions.update,
      tw,
      NewEditTrackingwordsComponent
    );
  }

  /*
Open a model to create a new Item
 * */
  newItem(): void {
    this.openModal(
      true,
      this.trackingwordService.permissions.save,
      ({} = {} as ITrackingWords),
      NewEditTrackingwordsComponent
    );
  }

  /**
   * change item status (active or deactive)
   * @param row
   */
  toggleActive(row: any): void {
    if (row.active) {
      this.modalService.openConfirmation({
        message:
          this.translateHelper.instant('label.confirm_activate') +
          ' ' +
          row.word +
          '?',
        onConfirm: () => {
          this.trackingwordService.update(row, (response) => {
            this.loadTable();
          });
        },
      });
    } else {
      this.modalService.openConfirmation({
        message:
          this.translateHelper.instant('label.confirm_activate') +
          ' ' +
          row.word +
          '?',
        onConfirm: () => {
          this.trackingwordService.update(row, (response) => {
            this.loadTable();
          });
        },
      });
    }
  }

  /**
   *   change paginator
   * @param pageOfItems
   */
  onChangePage(pageOfItems: Array<any>): void {
    this.copyArray = pageOfItems;
  }

  /**
   *   search items in table with the filters values
   * @param value
   */
  searchResult(): void {
    if (this.filter.feeling !== '') {
      this.filteredTracking = this.allTracking.filter(
        (u) =>
          u.word.toLowerCase().includes(this.filter.word.toLowerCase()) &&
          u.feeling === this.filter.feeling &&
          u.active === this.filter.active
      );
    } else {
      this.filteredTracking = this.allTracking.filter(
        (u) =>
          u.word.toLowerCase().includes(this.filter.word.toLowerCase()) &&
          u.active === this.filter.active
      );
    }
  }

  /**
   * Order table Items
   * @param colName
   */
  sortFunction(colName: string): void {
    const result = this.utilService.sortTable(
      colName,
      true,
      this.filteredTracking,
      this.sortDirection
    );
    this.filteredTracking = result.array;
    this.sortDirection = result.direction;
    this.utilService.changeSelectedPageToFirst(this.paginatorClass);
  }

  /**
   * clean Filters
   */
  clean(): void {
    this.filter = {
      word: '',
      feeling: '',
      active: true,
    };
    this.filteredTracking = this.allTracking;
  }

  /**
   * download excel file
   * @param title
   */
  downloadExcel(title: string) {
    this.trackingwordService.list((response) => {
      response = this.cleanTable(response);
      this.utilService.downloadDataToExcel(title, response);
    });
  }

  /**
   * clean table to export
   * @param response
   */
  cleanTable(response: any[]): any {
    let newArray = [];

    const headerName = this.translateHelper.instant('label.name');
    const headerFeeling = this.translateHelper.instant(
      'label.tracking.feelings'
    );
    const headerAssignment = this.translateHelper.instant('label.assignments');

    if (response) {
      response.sort((a, b) => a.word.localeCompare(b.word));

      response.forEach((element) => {
        let newElement = {};
        newElement[headerName] = element.word;
        newElement[headerFeeling] = this.translateHelper.instant(
          'feeling.' + element.feelingObj.tag
        );
        newElement[headerAssignment] = element.totalAsigned;
        newArray.push(newElement);
      });
    }

    return newArray;
  }
}
