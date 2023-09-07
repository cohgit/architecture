import {Component, OnInit} from '@angular/core';
import {NewEditForbiddenWordsComponent} from './new-edit-forbidden-words/new-edit-forbidden-words.component';
import {MatDialog} from '@angular/material/dialog';
import {ForbiddenWordService} from '../../services/forbidden-word.service';
import {TranslateHelperService} from '../../helpers/translate-helper.service';
import {UtilService} from '../../helpers/util.service';
import {ModalService} from '../../helpers/modal.service';
import {CommonService} from '../../services/common.service';

@Component({
  selector: 'app-forbidden-words',
  templateUrl: './forbidden-words.component.html',
  styleUrls: ['./forbidden-words.component.css']
})
export class ForbiddenWordsComponent implements OnInit {
  allForbbiden: any = [];
  filteredForbbiden: any = [];
  copyArray: Array<any>;
  /* objeto a enviar al filtro */
  filter = {
    word: '',
  };
  sortDirection = false;
  paginatorClass = 'paginator';

  /**
   * manage forbidden words
   * @param dialog
   * @param modalService
   * @param utilService
   * @param forbiddenWordServices
   * @param translateHelper
   * @param commonService
   */
  constructor(private dialog: MatDialog, private modalService: ModalService, private utilService: UtilService,
              public forbiddenWordServices: ForbiddenWordService, public translateHelper: TranslateHelperService,
              private commonService: CommonService) {

  }

  ngOnInit(): void {
    this.loadTable();
  }


  /*
  load principal table
   * */
  loadTable(): void {
    this.forbiddenWordServices.list(response => {
      this.allForbbiden = response;
      this.allForbbiden.sort((a, b) => a.word.localeCompare(b.word));
      this.filteredForbbiden = this.allForbbiden;
    });
  }

  /**
   *   function to open modal
   * @param isNuevo
   * @param isEditable
   * @param trackingword
   * @param componente
   */
  openModal(isNuevo: boolean, isEditable: boolean, data: any, componente): void {
    const dialogRef = this.dialog.open(componente, {
      closeOnNavigation: true,
      disableClose: true,
      data: {
        nuevo: isNuevo,
        editable: isEditable,
        info: data
      }
    });
    dialogRef.afterClosed().subscribe(result => {
      this.loadTable();
    });
  }

  /**
   *  Open a model to edit an Item
   * @param tw
   */
  editItem(tw: any): void {
    this.openModal(false, this.forbiddenWordServices.permissions.update, tw, NewEditForbiddenWordsComponent);
  }

  /*
Open a model to create a new Item
 * */
  newItem(): void {
    this.openModal(true, this.forbiddenWordServices.permissions.save, {} = ({} as any), NewEditForbiddenWordsComponent);
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

    this.filteredForbbiden = this.allForbbiden.filter(u =>
      u.word.toLowerCase().includes(this.filter.word.toLowerCase())
    );

  }

  /**
   * Order table Items
   * @param colName
   */
  sortFunction(colName: string): void {
    const result = this.utilService.sortTable(colName, true, this.filteredForbbiden, this.sortDirection);
    this.filteredForbbiden = result.array;
    this.sortDirection = result.direction;
    this.utilService.changeSelectedPageToFirst(this.paginatorClass);
  }

  /**
   * clean Filters
   */
  clean(): void {
    this.filter = {
      word: '',
    };
    this.filteredForbbiden = this.allForbbiden;
  }

  /**
   * delete items from an array after confrim
   * @param data
   */
  deleteItem(data: any) {
    this.modalService.openConfirmation({
      message: this.translateHelper.instant('label.confirm_delete') +
        ' ' + data.word + '?',
      onConfirm: () => {
        this.forbiddenWordServices.delete(data.id, response => {
          this.loadTable();
        });
      },
    });
  }
}
