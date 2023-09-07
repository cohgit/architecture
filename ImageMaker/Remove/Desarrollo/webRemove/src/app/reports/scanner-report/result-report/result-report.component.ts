import {Component, Input, OnInit} from '@angular/core';
import {Observable, Observer} from "rxjs";


@Component({
  selector: 'app-result-report',
  templateUrl: './result-report.component.html',
  styleUrls: ['./result-report.component.css']
})
export class ResultReportComponent implements OnInit {
  @Input() infoResults: any;


  constructor() {
  }

  ngOnInit(): void {

  }

  checkLengthAll(snippet) {
    if (snippet?.length <= 1) {
      return 'col-md-12';
    } else {
      return 'col-md-6';
    }
  }

  checkLengthNews(snippet) {
    if (snippet?.length <= 1) {
      return 'col-md-12';
    } else {
      return 'col-md-4';
    }
  }

}
