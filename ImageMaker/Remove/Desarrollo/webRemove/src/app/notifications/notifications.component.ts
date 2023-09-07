import {Component, OnInit} from '@angular/core';
import {TranslateHelperService} from '../helpers/translate-helper.service';
import {CommonService} from '../services/common.service';
import {Router} from '@angular/router';
import {UtilService} from '../helpers/util.service';
import {MatSlideToggleChange} from '@angular/material/slide-toggle';
import {SessionService} from '../helpers/session.service';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.css']
})
export class NotificationsComponent implements OnInit {
  allNotification = [];
  filteredNotification = [];
  open = true;
  copyArray: Array<any>;
  filter = {
    recive_date_init: new Date(),
    recive_date_end: new Date(),
  };
  today = new Date();
  objFilter = ['creation_date'];
  arrItems = [];
  showHiddenNot = false;
  pag: number;
  sortDirection = false;
  isAdmin = false;

  /**
   * Manage system notifications
   * @param commonService
   * @param translateHelper
   * @param router
   * @param utilService
   * @param session
   */
  constructor(private commonService: CommonService, public translateHelper: TranslateHelperService,
              private router: Router, public utilService: UtilService, public session: SessionService) {
  }

  ngOnInit(): void {
    this.loadNotifications();
    if (this.session.getAccessConfig().ADMIN) {
      this.isAdmin = true;
    }
  }

  /**
   * load notificactions lists
   */
  loadNotifications() {
    this.commonService.listNotifications(null, response => {
      this.allNotification = response;
      this.filteredNotification = this.allNotification;
    });
  }

  /**
   *   function to control the pager
   * @param pageOfItems
   */
  onChangePage(pageOfItems: Array<any>): void {
    this.copyArray = pageOfItems;
  }

  /**
   *  check if a notifications is readed or not
   * @param item
   */
  checkOpen(item: any) {
    if (!item.readed) {
      this.commonService.markNotificationsAsReaded([item], true, response => {
        item.readed = true;
      });
    }
  }

  /**
   *  redirects to the scanner of the selected client
   * @param type
   * @param uuid
   */
  checkScanner(type: string, uuid: string) {
    if (uuid) {
      if (!this.isAdmin) {
        this.router.navigate(['client/module/scanner/' + type + '/' + uuid + "/view"]);
      } else {
        this.router.navigate(['admin/module/scanner/' + type + '/' + uuid + "/view"]);
      }
    } else {
      if (!this.isAdmin) {
        this.router.navigate(['client/module/global_view/' + type]);
      } else {
        this.router.navigate(['admin/module/global_view/' + type]);
      }
    }


  }

  /**
   * redirects to the boost module of the selected client
   * @param uuid
   */
  goImpulse(uuid: string) {
    if (!this.isAdmin) {
      this.router.navigate(['client/module/impulse' + '/' + uuid]);
    } else {
      this.router.navigate(['admin/module/impulse' + '/' + uuid]);
    }

  }

  /**
   * redirects to clients module
   */
  goClients() {
    this.router.navigate(['admin/module/clients']);
  }

  /**
   * filter array to search
   * @param value
   */
  searchResult(value: any) {
    const formatDates = {
      recive_date: {
        init: new Date(value.recive_date_init).toLocaleDateString(),
        end: new Date(value.recive_date_end).toLocaleDateString(),
      }
    };
    // this.copyArray = this.utilService.filterResult(this.objFilter, formatDates, this.allClients);
    this.filteredNotification = this.allNotification.filter(u =>
      (new Date(u.creation_date).toLocaleDateString() <= formatDates.recive_date.end &&
        new Date(u.creation_date).toLocaleDateString() >= formatDates.recive_date.init));
  }

  /**
   *  add or slice checked items to an array
   * @param detail
   * @param event
   */
  addItems(detail: any, event) {
    if (event.checked) {
      this.arrItems.push(detail);
    } else {
      this.arrItems.forEach(item => {
        if (item === detail) {
          const index = this.arrItems.findIndex(i => i === detail);
          this.arrItems.splice(index, 1);
        }
      });
    }
  }

  /**
   * mark an array of elements as hidden
   */
  hideChecked() {
    const isHide = true;
    if (this.arrItems.length !== 0) {
      this.commonService.markNotificationsAsHidden(this.arrItems, isHide, response => {
        this.loadNotifications();
      });
    }
  }
  /**
   * mark an array of elements as read
   */
  markReaded() {
    const readed = true;
    if (this.arrItems.length !== 0) {
      this.commonService.markNotificationsAsReaded(this.arrItems, readed, response => {
        this.loadNotifications();
      });
    }
  }
  /**
   * show all elementes hidden
   */
  showHidden(event: MatSlideToggleChange) {
    this.showHiddenNot = event.checked;
  }

  /**
   * clear filter
   */
  clean() {
    this.filter = {
      recive_date_init: new Date(),
      recive_date_end: new Date(),
    };
    this.filteredNotification = this.allNotification;
  }

  /**
   * Sort table by a firs level param
   * @param colName
   * @param ckeck
   */
  sortFunction(colName, ckeck): void {
    this.copyArray = this.utilService.sortTable(colName, ckeck, this.copyArray, this.sortDirection).array;
    this.sortDirection = this.utilService.sortTable(colName, ckeck, this.copyArray, this.sortDirection).direction;
  }
}
