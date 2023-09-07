import { Component, Input, OnInit } from '@angular/core';
import { UtilService } from '../../../../../helpers/util.service';

@Component({
  selector: 'app-alert-sections',
  templateUrl: './alert-sections.component.html',
  styleUrls: ['./alert-sections.component.css'],
})
export class AlertSectionsComponent implements OnInit {
  @Input() content;
  @Input() scannerInfo;
  @Input() isOneShot;
  copyArray: Array<any>;
  sortDirection = false;
  paginatorClass = 'paginator_' + Math.floor(Math.random() * 1000000);

  /**
   * new or negative content table
   * @param utilService
   */
  constructor(public utilService: UtilService) {}

  ngOnInit(): void {
    //console.log('this.content', this.content);
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
    const result = this.utilService.sortTable(
      colName,
      true,
      this.content,
      this.sortDirection
    );
    this.content = result.array;
    this.sortDirection = result.direction;
    this.utilService.changeSelectedPageToFirst(this.paginatorClass);
  }
}
