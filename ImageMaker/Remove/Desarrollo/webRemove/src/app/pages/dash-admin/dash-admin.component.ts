import {Component, OnInit} from '@angular/core';
import {DashboardService} from '../../services/dashboard.service';
import {UtilService} from '../../helpers/util.service';

@Component({
  selector: 'app-dash-admin',
  templateUrl: './dash-admin.component.html',
  styleUrls: ['./dash-admin.component.css']
})
export class DashAdminComponent implements OnInit {
  dashData: any;
  istrue = true;
  isFalse = false;
  legendBelow = 'below';
  legendLabel = '';
  colorScheme = {
    domain: ['#0A4E7D', '#6c0a07', '#000000', '#86d7db', '#00769e', '#00c299', '#dfa01f', '#897456', '#77354d', '#fff7d6', '#614617', '#77354d']
  };
  copyArray: Array<any>;

  /**
   * mangae dashboard admin
   * @param dashboardService
   * @param utilService
   */
  constructor(private dashboardService: DashboardService, public utilService: UtilService) {
  }

  ngOnInit(): void {
    this.dashboardService.get(null, response => {
      if (response?.serps) {
        response.serps.percentage = (response.serps.consumed / response.serps.total) * 100;
      }

      this.dashData = response
    });
  }

  /**
   * change paginator
   * @param pageOfItems
   */
  onChangePage(pageOfItems: Array<any>) {
    this.copyArray = pageOfItems;
  }
}
