import {Component, Inject, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-scanner-report',
  templateUrl: './scanner-report.component.html',
  styleUrls: ['./scanner-report.component.css']
})
export class ScannerReportComponent implements OnInit {
  @Input() infoScan: any;
  @Input() infoDash: any;
  @Input()  infoResults: any;

  /**
   * DEPRECATED for advance report in pdf screenshot
   */
  constructor() {

  }

  ngOnInit(): void {

  }


}
