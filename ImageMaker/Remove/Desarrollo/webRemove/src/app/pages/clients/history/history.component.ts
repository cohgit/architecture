import {Component, Input, OnInit} from '@angular/core';
import {TranslateService} from "@ngx-translate/core";
import {UtilService} from "../../../helpers/util.service";

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})
export class HistoryComponent implements OnInit {
  @Input() historial: any = {};
  copyArray: Array<any>;

  /**
   * shows history of actions
   * @param translate
   * @param utilService
   */
  constructor(public translate: TranslateService, public utilService: UtilService) {

  }

  ngOnInit(): void {
  }

  /**
   * change paginator
   * @param pageOfItems
   */
  onChangePage(pageOfItems: Array<any>): void {
    this.copyArray = pageOfItems;
  }
}
