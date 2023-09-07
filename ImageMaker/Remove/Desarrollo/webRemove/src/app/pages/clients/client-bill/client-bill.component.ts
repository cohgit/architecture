import {Component, Inject, Input, OnInit} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {UtilService} from '../../../helpers/util.service';

@Component({
  selector: 'app-client-bill',
  templateUrl: './client-bill.component.html',
  styleUrls: ['./client-bill.component.css']
})
export class ClientBillComponent implements OnInit {
  info: any = {};
  copyArray: Array<any>;
  @Input() infoBills: any = {};

  /**
   * show history of payments
   * @param translate
   * @param utilService
   */
  constructor(public translate: TranslateService,  public utilService: UtilService) {

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
