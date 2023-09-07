import {Component, Input, OnInit} from '@angular/core';
import {UtilService} from "../../../../../helpers/util.service";

@Component({
  selector: 'app-table-tracking-progress',
  templateUrl: './table-tracking-progress.component.html',
  styleUrls: ['./table-tracking-progress.component.css']
})
export class TableTrackingProgressComponent implements OnInit {
  @Input() title_code = '';
  @Input() listResultsTracks: any[] = [];
  @Input() showFlag: boolean;

  /**
   * manage the data for the
   * @param utilService
   */
  constructor(public utilService: UtilService) {
  }

  ngOnInit(): void {
  }
}
