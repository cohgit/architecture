import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {CommonService} from "../../../services/common.service";
import {MatPaginator} from "@angular/material/paginator";
import {UtilService} from "../../../helpers/util.service";
import {DomSanitizer} from '@angular/platform-browser';

@Component({
  selector: 'app-result-scanner',
  templateUrl: './result-scanner.component.html',
  styleUrls: ['./result-scanner.component.css']
})
export class ResultScannerComponent implements OnInit {
  @Input() filteredResults: any[] = [];
  @Input() expandBox: boolean;
  @Output() updateSnippetFeeling = new EventEmitter<any>();
  @Output() checkError = new EventEmitter<any>();
  @Input() isAdmin: boolean;
  expandedResult = false;
  allFeelings: any;
  snnipetsLists = [];
  noResult = false;
  errorInResult: any;
  @ViewChild('allPaginator', {read: MatPaginator}) allPaginator: MatPaginator;

  /**
   * manage the result tabs
   * @param commonService
   * @param utilService
   * @param domSanitizer
   */
  constructor(private commonService: CommonService, public utilService: UtilService, public domSanitizer: DomSanitizer) {



  }

  ngOnInit(): void {
    this.loadCommons();

  }
/**
 * 
 */
  checkDate(){
    this.errorInResult = false;
    const base = this.filteredResults[0].query_date;
    this.filteredResults.forEach(obj => {
      if (base !== obj.query_date) {
        this.errorInResult = true;
      }
    });
    this.checkError.emit(this.errorInResult);
  }

  /**
   *   load commons services to fill select inputs
   */
  loadCommons(): void {
    this.commonService.listFeelings(response => {
      this.allFeelings = response;
      this.checkDate();
     // console.log('this.errorInResult', this.errorInResult);
    });

  }

  /**
   * execute the change feeling action
   * @param snippet
   */
  onChangeFeeling(snippet: any) {
    let encontrado = false;
    if (this.snnipetsLists.length === 0) {
      this.snnipetsLists.push(snippet);
    } else {
      // Check if exist
      this.snnipetsLists.forEach(obj => {
        if (obj.id === snippet.id) {
          encontrado = true;
        }
      });
      // if dosen´t exists push it
      if (!encontrado) {
        this.snnipetsLists.push(snippet);
      } else {
        // if exist remove the old data and push the new data
        const index = this.snnipetsLists.findIndex(i => i.id === snippet.id);
        this.snnipetsLists.splice(index, 1);
        this.snnipetsLists.push(snippet);
      }
    }
    // this.updateSnippetFeeling.emit(snippet);
  }

  /**
   * send the array of the snippet that was changed
   */
  sendFeelings() {
    this.updateSnippetFeeling.emit(this.snnipetsLists);
  }

  /**
   * check if the array has an item
   * @param result
   */
  checkResult(result: any[]) {
    let found = false;
    let checkEmpty = 0;
    result.forEach(r => {
      if (r.snippetsImages.length === 0 && r.snippetsNews.length === 0 && r.snippetsWebs.length === 0) {
        //found = true;
        checkEmpty++;
      }
    });
    if (result.length === checkEmpty) {
      found = true;
    }
    return found;

  }

}
