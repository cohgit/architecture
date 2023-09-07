import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {CommonService} from 'src/app/services/common.service';
import {ScannerFilter} from 'src/app/interfaces/IScannerFilter'
import {UtilService} from 'src/app/helpers/util.service';
import {TranslateHelperService} from 'src/app/helpers/translate-helper.service';
import * as moment from 'moment';

@Component({
  selector: 'app-filters',
  templateUrl: './filters.component.html',
  styleUrls: ['./filters.component.css']
})
export class FiltersComponent implements OnInit {
  @Output() applyFilter = new EventEmitter<ScannerFilter>();
  @Output() clearFilter = new EventEmitter<ScannerFilter>();
  @Input() scannerInfo: any = {};
  @Input() col: any = {};
  @Input() applyButtonTitle: string = 'button.filter';
  @Input() filtersLists: any = {};
  @Input() filterModel: ScannerFilter = new ScannerFilter();

  private scanner: any;
  today = new Date();

  /**
   * Component that manages the filters of the scanner
   * @param util
   * @param translateHelper
   */
  constructor(private util: UtilService, public translateHelper: TranslateHelperService, public utilService: UtilService) {
  }

  ngOnInit(): void {
  }

  /**
   * Refresh filters to their base content
   * @param newScanner
   */
  refreshFilters(newScanner: any): void {
    this.scanner = newScanner;

    // this.clearFilters();
  }
/*
  refreshSuggestedKeywords(): void {
    delete this.filterModel.suggested_keywords;

    if (this.filterModel.keywords) {
      this.filtersLists.suggested_keywords = this.util.filterArray(this.scanner?.keywords, 'id_suggested_from', this.filterModel.keywords.id);
    } else {
      this.filtersLists.suggested_keywords = this.util.filterArray(this.scanner?.keywords, 'suggested', true);
    }
  }

  clearFilters(): void {
    this.refreshSuggestedKeywords();
    this.callFilterEvent();
  }
*/
  /**
   *
   * @param model
   */
  callFilterEvent(model?: any): void {
    if (model) {
     // console.log('model', model);
      this.filterModel = model;
    }

    if (this.filterModel.init_date && !this.filterModel.end_date) {
      this.filterModel.end_date = this.filterModel.init_date;
    }

    this.setListApplieds();
    this.applyFilter.emit(this.filterModel);
  }

  /**
   *clear filters
   */
  callClearFiltersEvent(): void {
    this.clearFilter.emit();
  }

  /**
   *set options to the selects
   */
  setListApplieds() {
    this.filterModel.applieds = [];

    if (this.filterModel.keywords) {
      this.filterModel.keywords.forEach(kw => {
        this.filterModel.applieds.push(kw.word);
      })
    }
    if (this.filterModel.suggested_keywords) {
      this.filterModel.suggested_keywords.forEach(kw => {
        this.filterModel.applieds.push(kw.word);
      });
    }
    if (this.filterModel.search_types) {
      this.filterModel.search_types.forEach(st => {
        this.filterModel.applieds.push(this.translateHelper.instant('list.scanner.type.' + st));
      });
    }
    if (this.filterModel.feelings) {
      this.filterModel.feelings.forEach(feel => {
        this.filterModel.applieds.push(this.translateHelper.instant('feeling.' + feel.tag));
      });
    }
    if (this.filterModel.tracking_words) {
      this.filterModel.tracking_words.forEach(tw => {
        this.filterModel.applieds.push(tw.word);
      });
    }
    if (this.filterModel.urls) {
      this.filterModel.urls.forEach(url => {
        this.filterModel.applieds.push(url.word);
      });
    }
    if (this.filterModel.countries) {
      this.filterModel.countries.forEach(country => {
        this.filterModel.applieds.push(this.translateHelper.instant('list.country.' + country.tag));
      });
    }
    if (this.filterModel.pages !== undefined && this.filterModel.pages.length > 0) {
      this.filterModel.applieds.push("P: " + this.filterModel.pages);
    }
    if (this.filterModel.init_date && this.filterModel.end_date) {
      this.filterModel.applieds.push(moment(this.filterModel.init_date).format('DD-MM-YYYY') + ' / ' + moment(this.filterModel.end_date).format('DD-MM-YYYY'));
    }
    if (this.filterModel.isNew) {
      this.filterModel.applieds.push(this.translateHelper.instant('label.new'));
    }

  }

}
