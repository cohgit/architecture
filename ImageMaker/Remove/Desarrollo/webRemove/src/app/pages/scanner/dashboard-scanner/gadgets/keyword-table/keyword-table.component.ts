import {Component, Input, OnInit} from '@angular/core';
import {UtilService} from 'src/app/helpers/util.service';

@Component({
  selector: 'app-keyword-table',
  templateUrl: './keyword-table.component.html',
  styleUrls: ['./keyword-table.component.css']
})
export class KeywordTableComponent implements OnInit {
  @Input() content;
  @Input() tittle;
  @Input()  isOneShot;
  @Input() scannerInfo;s

  paginatorClass = 'paginator_' + Math.floor(Math.random() * 1000000);

  copyArray: Array<any>;
  sortDirection = false;

  /**
   * Manage information about visibilty tables
   * @param utilService
   */
  constructor(public utilService: UtilService) {
  }

  ngOnInit(): void {
  }

  /**
   * for paginator
   * @param pageOfItems
   */
  onChangePage(pageOfItems: Array<any>) {
    this.copyArray = pageOfItems;
  }
  /**
   * Order table Items
   * @param colName
   */
  sortFunction(colName: string): void {
    const result = this.utilService.sortTable(colName, true, this.content, this.sortDirection);
    this.content = result.array;
    this.sortDirection = result.direction;
    this.utilService.changeSelectedPageToFirst(this.paginatorClass);
  }
}
