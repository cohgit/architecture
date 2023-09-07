import { Component, OnInit, ViewChild } from '@angular/core';
import { UtilService } from 'src/app/helpers/util.service';
import { ScannerFilter } from 'src/app/interfaces/IScannerFilter';
import { ScannerService } from 'src/app/services/scanner.service';
import { FiltersComponent } from 'src/app/shared/filters/filters.component';
import { TranslateHelperService } from 'src/app/helpers/translate-helper.service';
import { DashboardBuilderService } from 'src/app/helpers/dashboard-builder.service';
import { ScannerDashboard } from 'src/app/interfaces/IScannerDashboard';
import { ModalService } from 'src/app/helpers/modal.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ConfigScannerComponent } from './config-scanner/config-scanner.component';
import { MatDialog } from '@angular/material/dialog';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import * as moment from 'moment';
import { SessionService } from '../../helpers/session.service';
import { AdvaceReportComponent } from '../../reports/advace-report/advace-report.component';
import { environment } from 'src/environments/environment';
import { CommonService } from 'src/app/services/common.service';
import { ConfirmScannerComponent } from './config-scanner/confirm-scanner/confirm-scanner.component';
import { ScannerCommentsService } from '../../services/scanner-comments.service';

const TAB_INDEX_CONFIG = 0;
const TAB_INDEX_RESULT = 1;
const TAB_INDEX_DASHBOARD = 2;

@Component({
  selector: 'app-scanner',
  templateUrl: './scanner.component.html',
  styleUrls: ['./scanner.component.css'],
})
export class ScannerComponent implements OnInit {
  @ViewChild('filterComp') filterComponent: FiltersComponent;
  @ViewChild('configComp') configComponent: ConfigScannerComponent;
  isExecuting: any; //Deprecated
  isLoading: boolean = false;

  uuid: string;
  id_client: any;
  uuid_client: string;
  scanner_type: string;
  operation: string;
  initFilterDate: any;
  endFilterDate: any;

  events: string[] = [];
  tabIndex = TAB_INDEX_CONFIG;
  initialData: any;

  filterLists: any = {};
  filteredResults: any[] = [];
  filteredLastResults: any[] = [];
  listFiltersApplieds: string[] = [];
  filterModel: any = [];

  dashboardData: ScannerDashboard;

  tittle = '';
  expandBox = true;
  executingScanner = '';
  isFalse = false;
  isAdmin = false;

  // showReportsButton = !environment.production;
  // deprecated
  showReportsButton = true;
  hasErrorInDate = false;
  comments = [];
  /**
   * manage scanner information
   * @param scannerService
   * @param utils
   * @param dialog
   * @param translateHelper
   * @param dashboardBuilder
   * @param modal
   * @param activatedRoute
   * @param ngxLoader
   * @param commonService
   * @param router
   * @param session
   * @param commentService
   */
  constructor(
    private scannerService: ScannerService,
    public utils: UtilService,
    private dialog: MatDialog,
    public translateHelper: TranslateHelperService,
    public dashboardBuilder: DashboardBuilderService,
    private modal: ModalService,
    private activatedRoute: ActivatedRoute,
    private ngxLoader: NgxUiLoaderService,
    private commonService: CommonService,
    private router: Router,
    public session: SessionService,
    private commentService: ScannerCommentsService
  ) {}

  ngOnInit(): void {
    this.scanner_type = this.getScannerType();
    this.uuid_client = this.activatedRoute.snapshot.paramMap.get('uuid_client');
    this.uuid = this.activatedRoute.snapshot.paramMap.get('uuid_scanner');
    this.operation = this.activatedRoute.snapshot.paramMap.get('operation');
    this.id_client = this.activatedRoute.snapshot.paramMap.get('id');

    this.loadScanner();
    if (this.session.getAccessConfig().ADMIN) {
      this.isAdmin = true;
    }
    console.log('this.id_client init', this.id_client);
  }

  /**
   * load based information about a scanner
   */
  loadScanner() {
    if (!this.isLoading) {
      if (this.uuid) {
        this.isLoading = true;
        this.scannerService.get(this.uuid, this.scanner_type, (response) => {
          this.composeObject(response);
          this.tittle = this.utils.checkTitleScanner(response.type);

          this.isLoading = false;

          /*  if (response.executing) {
                if (!this.isExecuting) {
                  this.isExecuting = setInterval(() => {
                    this.loadScanner()
                  }, 60 * 1000);
                }
              } else {
                if (this.isExecuting) {
                  clearInterval(this.isExecuting);
                  this.isExecuting = null;
                  this.tabIndex = TAB_INDEX_RESULT;
                }
              }*/
        });
      } else {
        // this.uuid_client = this.activatedRoute.snapshot.paramMap.get('uuid_client');

        this.scannerService.getDefault(
          this.scanner_type,
          this.uuid_client,
          (response) => {
            this.initialData = response;
            this.tittle = this.utils.checkTitleScanner(response.type);
            this.filteredResults = [];
            this.filteredLastResults = [];
            this.dashboardData = null;
            this.configComponent?.markDisabledForm(this.initialData);
            this.comments = [];
          }
        );
      }
    }
  }

  /**
   * check if a scanner is executing or not
   * @param executing
   */
  checkExecuting(executing: boolean) {
    if (executing) {
      this.ngxLoader.startLoader('loader-02');
      this.executingScanner = this.translateHelper.instant(
        'message.executing_scanner'
      );
    } else {
      this.ngxLoader.stopLoader('loader-02');
    }
  }

  /**
   * Sets scanner data, build results object for 'Results' tab and Dashboard info for 'Dashboards' tab.
   * @param scanner
   */
  composeObject(scanner: any) {
    this.initialData = Object.assign({}, scanner);
    this.resetsFilteredResultsData();
    // this.filterComponent.refreshFilters(scanner);
    this.fillFilterLists();
    // this.buildDashboard();
    this.configComponent.checkDisableCountries();
    this.configComponent.setTotalKeywordsSelected(scanner);
    this.configComponent?.markDisabledForm(scanner);
    this.checkExecuting(this.initialData.executing);
    this.setFilterDefaultValues();
    this.loadComments();
  }

  /**
   * set filters data
   */
  fillFilterLists() {
    this.commonService.listFeelings((response) => {
      this.filterLists.feelings = response;
      this.filterLists.keywords = this.utils.filterArray(
        this.initialData?.keywords,
        'suggested',
        false
      );
      this.filterLists.tracking_words = this.utils.filterArray(
        this.initialData?.trackingWords,
        'type',
        'WORD'
      );
      this.filterLists.urls = this.utils.filterArray(
        this.initialData?.trackingWords,
        'type',
        'URL'
      );
      this.filterLists.search_types =
        this.initialData?.configuration?.search_type.split(',');
      this.filterLists.pages = Array.from(
        { length: this.initialData?.configuration?.pages },
        (_, i) => i + 1
      );
      this.filterLists.countries = this.initialData?.configuration?.countries;
      this.filterComponent.callFilterEvent();
    });
  }

  /**
   * Resets results object for 'Results' Tab.
   */
  resetsFilteredResultsData() {
    this.filteredResults = this.utils.cloneObject(this.initialData?.results);
    // this.filteredLastResults = this.filterJustLastResults(this.filteredResults);
  }

  /**
   *
   * @param allResults
   * @returns
   */
  filterJustLastResults(allResults: any[], finalDate?: any): any[] {
    const filteredArray = this.utils.cloneObject(allResults);

    if (this.scanner_type !== 'one_shot') {
      if (filteredArray) {
        filteredArray.forEach((result) => {
          result.snippetsImages.forEach((snippet) =>
            this.filterLastTracking(snippet, result.query_date, finalDate)
          );
          result.snippetsNews.forEach((snippet) =>
            this.filterLastTracking(snippet, result.query_date, finalDate)
          );
          result.snippetsWebs.forEach((snippet) =>
            this.filterLastTracking(snippet, result.query_date, finalDate)
          );
        });
      }

      this.removeWithoutTracking(filteredArray);
      this.sortResults(filteredArray);
    }

    return filteredArray;
  }

  /**
   *
   * @param snippet
   * @param lastScan
   */
  filterLastTracking(snippet: any, lastScan: string, lastDate?: any) {
    if (lastDate) {
      snippet.tracking = snippet.tracking.filter((track) => {
        return moment(lastDate).isSame(moment(track.date_scan), 'day');
      });
    } else {
      snippet.tracking = snippet.tracking.filter((track) => {
        return track.date_scan === lastScan;
      });
    }
  }

  /**
   *
   * @param filteredArray
   */
  removeWithoutTracking(filteredArray: any) {
    filteredArray.forEach((result) => {
      result.snippetsImages = result.snippetsImages.filter(
        (snippet) => snippet.tracking.length > 0
      );
      result.snippetsNews = result.snippetsNews.filter(
        (snippet) => snippet.tracking.length > 0
      );
      result.snippetsWebs = result.snippetsWebs.filter(
        (snippet) => snippet.tracking.length > 0
      );
    });
  }

  /**
   *
   * @param results
   */
  sortResults(results: any[]) {
    results.forEach((result) => {
      if (result.snippetsImages) {
        result.snippetsImages = result.snippetsImages.sort(this.sortSnippet);
      }
      if (result.snippetsNews) {
        result.snippetsNews = result.snippetsNews.sort(this.sortSnippet);
      }
      if (result.snippetsWebs) {
        result.snippetsWebs.sort(this.sortSnippet);
      }
    });
    results = results.sort(this.sortResultsByContent);
  }

  /**
   *
   * @param a
   * @param b
   * @returns
   */
  sortSnippet(a: any, b: any): number {
    return (
      a?.tracking[a.tracking.length - 1]?.position -
      b?.tracking[b.tracking.length - 1]?.position
    );
  }

  /**
   * sort results
   * @param a
   * @param b
   */
  sortResultsByContent(a: any, b: any): number {
    if (a.keyword.word < b.keyword.word) {
      return -1;
    }
    if (a.keyword.word > b.keyword.word) {
      return 1;
    }
    if (a.search_type < b.search_type) {
      return -1;
    }
    if (a.search_type > b.search_type) {
      return 1;
    }
    return 0;
  }

  /**
   * ??? ELIMINAR
   * @param e
   */
  /*checkTab(e: any) {
    this.opened = false;
    this.tabIndex = 0;
    this.tabIndex = e.index;
    if (this.tabIndex !== 0) {
      this.opened = true;
    }
  }
*/
  /**
   * clean config fields (DEPRECADO)
   *
   */

  /*clean(): void{
    this.modal.openConfirmation({
      message: 'message.confirmation.scanner.clean',
      onConfirm: () => {
        this.scannerService.getDefault(this.scanner_type, this.uuid_client, response => {
          this.initialData = response;
          this.filteredResults = [];
          this.filteredLastResults = [];
          this.dashboardData = null;
        });
      }
    });
  }*/
  /**
   * Apply filter to results scanner array
   * @param $event ScannerFilter object with filter values
   */
  filterResults($event: ScannerFilter) {
    // console.log('filter result',  $event);
    this.listFiltersApplieds = $event.applieds;
    this.initFilterDate = $event.init_date;
    this.endFilterDate = $event.end_date;
    this.filterModel = $event;
    this.cleanFilter($event);
    this.resetsFilteredResultsData();

    if ($event.keywords) {
      this.filteredResults = this.utils.filterArrayOrConditions(
        this.filteredResults,
        ['id_keyword', 'id_parent_keyword'],
        this.utils.extractAttrArray($event.keywords, 'id')
      );
    }
    if ($event.suggested_keywords) {
      this.filteredResults = this.utils.filterArrayOrConditions(
        this.filteredResults,
        ['id_keyword'],
        this.utils.extractAttrArray($event.suggested_keywords, 'id')
      );
    }
    if ($event.search_types) {
      this.filteredResults = this.utils.filterArrayOrConditions(
        this.filteredResults,
        ['search_type'],
        $event.search_types
      );
    }
    if ($event.countries) {
      this.filteredResults = this.utils.filterArrayOrConditions(
        this.filteredResults,
        ['id_country'],
        this.utils.extractAttrArray($event.countries, 'id')
      );
    }
    if (
      $event.feelings ||
      $event.tracking_words ||
      $event.urls ||
      $event.countries ||
      ($event.pages && $event.pages.length > 0) ||
      ($event.init_date && $event.end_date) ||
      $event.isNew
    ) {
      this.filterInnerObjects(this.filteredResults, $event);
    }

    this.filteredLastResults = this.filterJustLastResults(
      this.filteredResults,
      $event.end_date
    );
    //console.log('filteredLastResults', this.filteredLastResults);
    this.buildDashboard($event);
  }

  /**
   *
   * @param $event Cleans filter for empty data
   */
  cleanFilter($event: ScannerFilter) {
    if ($event.feelings && $event.feelings.length === 0) {
      delete $event.feelings;
    }
    if ($event.keywords && $event.keywords.length === 0) {
      delete $event.keywords;
    }
    if ($event.tracking_words && $event.tracking_words.length === 0) {
      delete $event.tracking_words;
    }
    if ($event.urls && $event.urls.length === 0) {
      delete $event.urls;
    }
  }

  /**
   * Send scanner to api service for results generations
   * @param $event
   */
  generateResults($event: any) {
    $event.type = this.scanner_type;
    console.log('this.id_client', this.id_client);
    if (this.operation === 'clone') {
      $event.uuid_from = this.uuid;
    }

    this.cleanScanner($event);
    this.scannerService.save($event, (response) => {
      this.uuid = response.uuid;
      this.composeObject(response);

      if (response.executing && !this.isExecuting) {
        /*
        this.isExecuting = setInterval(() => {
          this.loadScanner();
        }, 60 * 1000);*/

        if (this.isAdmin) {
          console.log(
            '/admin/module/scanner/global_view/' +
              this.scanner_type +
              '/' +
              this.uuid_client +
              '/' +
              this.id_client
          );
          setTimeout(() => {
            this.router.navigate([
              '/admin/module/scanner/global_view/' +
                this.scanner_type +
                '/' +
                this.uuid_client +
                '/' +
                this.id_client,
            ]);
          }, 10000);
        } else {
          setTimeout(() => {
            this.router.navigate([
              '/client/module/scanner/global_view/' + this.scanner_type,
            ]);
          }, 10000);
        }
      } else {
        //   clearInterval(this.isExecuting);
        //  this.isExecuting = null;
        // TODO: step results
      }
    });
  }

  /**
   * Temporal method for cleaning keywords (Set child keywords as checked = false)
   * @param $event
   */
  cleanScanner($event: any) {
    $event?.keywords?.forEach((key) => {
      key?.listSuggested.forEach((sugg) => (sugg.checked = false));
    });
  }

  /**
   * Update Scanner Configurations
   * @param $event
   */
  updateConfiguration($event: any) {
    const dialogRef = this.dialog.open(ConfirmScannerComponent, {
      width: '60%',
      disableClose: true,
      data: {
        info: this.initialData,
        isNew: false,
        message: 'message.confirmation.scanner.update.configuration',
        tittle: 'tittle.confirmation.scanner.update.configuration',
      },
    });
    dialogRef.afterClosed().subscribe((result) => {
      console.log('result , en actualizar', result);
      if (result) {
        $event.type = this.scanner_type;
        $event.action = result.action;
        this.scannerService.update($event, (response) => {
          this.removeList(this.initialData.justTrackingWords);
          this.removeList(this.initialData.justTrackingURLs);
        });
      }
    });
  }
  // DEPRECATED incidencia SYMR-9
  /* this.modal.openConfirmation({
      message: 'message.confirmation.scanner.update.configuration',
      onConfirm: () => {
        $event.type = this.scanner_type;
        this.scannerService.update($event, response => {
          this.removeList(this.initialData.justTrackingWords);
          this.removeList(this.initialData.justTrackingURLs);
        });
      },
    });
  }*/

  /**
   * remove items from an array
   * @param arr
   */
  removeList(arr: any) {
    arr.forEach((val) => {
      if (val.markToDelete) {
        this.utils.removefromArray(val, arr);
      }
    });
  }
/**
 *
 * @param $event 
 */
  checkError($event: any) {
    this.hasErrorInDate = $event;
  }
  /**
   *
   * @param $event
   */
  changeSnippetFeeling($event: any) {
    let confirm = false;
    this.modal.openConfirmation({
      message: 'message.confirmation.update.snippet.feeling',
      onConfirm: () => {
        confirm = true;
        this.scannerService.updateSnippet($event);
      },
      afterClosed: () => {
        if (!confirm) {
          $event.feeling = $event.feelingObj.tag;
        }
      },
    });
  }

  /**
   * Apply filters to scanner results inner objects.
   * @param filteredResults Filter snippets from results by filter
   * @param $event
   */
  filterInnerObjects(filteredResults: any[], $event: ScannerFilter) {
    if (this.filteredResults) {
      filteredResults.forEach((result) => {
        if (result.snippetsWebs && result.snippetsWebs.length > 0) {
          result.snippetsWebs = this.filterSnippetsV2(
            result.snippetsWebs,
            $event
          );
        }
        if (result.snippetsNews && result.snippetsNews.length > 0) {
          result.snippetsNews = this.filterSnippetsV2(
            result.snippetsNews,
            $event
          );
        }
        if (result.snippetsImages && result.snippetsImages.length > 0) {
          result.snippetsImages = this.filterSnippetsV2(
            result.snippetsImages,
            $event
          );
        }
      });
    }
  }

  /**
   * filter results snnipets
   * @param snippets
   * @param $event
   */
  filterSnippetsV2(snippets: any[], $event: ScannerFilter): any[] {
    if (snippets?.length > 0) {
      return snippets.filter((snippet) => {
        // Filter Feelings
        if ($event?.feelings?.length > 0) {
          let feelingFound = false;
          $event.feelings.forEach((filterFeel) => {
            if (snippet.feeling === filterFeel.tag) {
              feelingFound = true;
            }
          });

          if (!feelingFound) return false;
        }

        // Filter TrackingWords
        if ($event?.tracking_words?.length > 0) {
          let twFound = false;

          snippet.trackingWords.forEach((snippetTw) => {
            $event.tracking_words.forEach((filterTw) => {
              if (snippetTw.word === filterTw.word) {
                twFound = true;
              }
            });
          });

          if (!twFound) return false;
        }

        // Filter URLS
        if ($event?.urls?.length > 0) {
          let urlFound = false;
          snippet.trackingWords.forEach((snippetUrl) => {
            $event.urls.forEach((filterUrl) => {
              if (snippetUrl.word === filterUrl.word) {
                urlFound = true;
              }
            });
          });

          if (!urlFound) return false;
        }

        if (snippet?.tracking?.length > 0) {
          snippet.tracking = snippet.tracking.filter((track) => {
            // Filter Pages
            if ($event?.pages?.length > 0) {
              let pageFound = $event.pages.includes(track.page);
              if (!pageFound) return false;
            }

            // Filter isNew
            if ($event.isNew && this.initialData?.results?.length > 0) {
              if (
                !moment(track.date_scan).isSame(
                  moment(
                    this.initialData.results[
                      this.initialData.results.length - 1
                    ].query_date
                  ),
                  'day'
                )
              ) {
                return false;
              }
            }

            // Filter Dates
            if ($event?.init_date && $event?.end_date) {
              let compareDate = moment(track.date_scan);
              if (
                !(
                  compareDate.isSameOrAfter($event.init_date, 'day') &&
                  compareDate.isSameOrBefore($event.end_date, 'day')
                )
              )
                return false;
            }

            return true;
          });
        }

        return snippet?.tracking?.length > 0;
      });
    }
    return [];
  }

  /**
   * Build dashboards data with dashboardBuilder's help.
   */
  buildDashboard(filters?: any) {
    if (this?.filteredResults?.length > 0) {
      this.dashboardData = {};
      this.dashboardData.v2 = this.dashboardBuilder.buildV2(
        this.scanner_type,
        this.filteredResults,
        this.initialData,
        filters
      );
    } else {
      this.dashboardData = null;
    }
  }

  /**
   * get type of the scanner
   * @private
   */
  private getScannerType(): string {
    const module_scanner = '/module/scanner/';
    if (window.location.href.includes(module_scanner)) {
      return window.location.href
        .split(module_scanner)[1]
        .split('/')[0]
        .split('?')[0];
    }

    return null;
  }

  /**
   * download report pdf
   */
  download() {
    const typeReport = {
      scan: true,
    };
    this.dialog.open(AdvaceReportComponent, {
      closeOnNavigation: true,
      disableClose: true,
      data: {
        filterLists: this.filterLists,
        filters: Object.assign({}, this.filterModel),
        initialData: this.initialData,
      },
    });
  }

  /**
   * default value to the filters
   */
  setFilterDefaultValues() {
    this.filterModel = new ScannerFilter();
    this.filterModel.pages = [1, 2];

    let endDate = this.initialData?.results[0]?.query_date
      ? moment(this.initialData?.results[0]?.query_date)
      : moment();
    let endDateReference = this.initialData?.results[0]?.query_date
      ? moment(this.initialData?.results[0]?.query_date)
      : moment();
    endDateReference.subtract(1, 'month');
    let initDate = this.initialData?.creation_date
      ? moment(this.initialData?.creation_date)
      : moment();
    initDate = initDate.isBefore(endDateReference)
      ? endDateReference
      : initDate;
    this.filterModel.init_date = initDate.toDate();
    this.filterModel.end_date = endDate.toDate();
    this.filterModel.countries = this.initialData?.configuration?.countries;
    this.filterModel.keywords = this.initialData?.keywords;
  }

  /**
   * clear filters and restore to the default value
   */
  clearFilters(): void {
    this.setFilterDefaultValues();
    this.filterComponent.callFilterEvent(this.filterModel);
  }

  /**
   * load  comment from scanner
   */
  loadComments() {
    this.commentService.listComments(this.initialData.id, (response) => {
      this.comments = response;
    });
  }
}
