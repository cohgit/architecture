import {Component, Input, OnInit} from '@angular/core';
import {UtilService} from "../../../helpers/util.service";
import {CommonService} from "../../../services/common.service";

@Component({
  selector: 'app-config-report',
  templateUrl: './config-report.component.html',
  styleUrls: ['./config-report.component.css']
})
export class ConfigReportComponent implements OnInit {
  @Input() infoScan: any;
  areSuggest = false;
  constructor(public utilService: UtilService, public commonService: CommonService) { }

  ngOnInit(): void {
this.checkSuggested();
  }

  checkSuggested(){
    this.infoScan.keywords.forEach(key => {
      if (key.listSuggested.length !== 0){
        this.areSuggest = true;
      }});
  }
}
