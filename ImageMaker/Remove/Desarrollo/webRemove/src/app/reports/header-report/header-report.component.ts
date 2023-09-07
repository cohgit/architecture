import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-header-report',
  templateUrl: './header-report.component.html',
  styleUrls: ['./header-report.component.css']
})
export class HeaderReportComponent implements OnInit {
  @Input() tittle: string;
  constructor() { }

  ngOnInit(): void {

  }

}
