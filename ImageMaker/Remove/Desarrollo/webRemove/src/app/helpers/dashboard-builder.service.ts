import { LAST_MEDIA } from '@angular/cdk/keycodes';
import { Injectable } from '@angular/core';
import * as moment from 'moment';

import {
  IScannerGraph,
  ScannerDashboardV2,
} from 'src/app/interfaces/IScannerDashboardV2';
import { TranslateHelperService } from './translate-helper.service';
import { UtilService } from './util.service';

const GREEN = '#54E887';
const RED = '#FF504A';
const YELLOW = '#E3D042';
const BLUE = '#3C9BE8';
const GRAY = '#bcd3df';

@Injectable({
  providedIn: 'root',
})
/**
 * Dashboard builder helper.
 */
export class DashboardBuilderService {
  private MIN_POSITIONS_IN_URL_TIME_LINEAL = 20;
  private MAX_POSITIONS_IN_URL_TIME_LINEAL = 1;

  /**
   *
   * It allows to build the dashboard of the scanners in the frontend of the project.
   * @param translate
   * @param utilService
   */
  constructor(
    public translate: TranslateHelperService,
    private utilService: UtilService
  ) {}

  /**
   * Obtain list of tracking words from a snippet (just the word)
   * @param snippet Snippet to inspect
   * @param type Tracking Word type (Possible values: 'WORD' or 'URL')
   * @returns An array of tracking words or URLs.
   */
  private hasTrackingWordOrUrl(snippet: any, type: string): string[] {
    const found = [];
    if (snippet.trackingWords && snippet.trackingWords.length > 0) {
      snippet.trackingWords.forEach((tw) => {
        if (tw.type === type) {
          found.push(tw);
        }
      });
    }

    return found;
  }

  /**
   * Build the description of a snippet with all its components
   * @param snippet
   * @param keyword
   * @param countryTag
   */
  buildLineDescription(
    snippet: any,
    keyword: string,
    countryTag: string
  ): string {
    return (
      this.utilService.onlyDomain(snippet.snippet.link) +
      ' * ' +
      keyword +
      ' * ' +
      this.translate.translate.instant('list.country.' + countryTag) +
      ' * ' +
      this.translate.translate.instant(
        'list.scanner.type.' + snippet.snippet.searchType
      ) +
      ' * ' +
      this.translate.translate.instant('label.ranking')
    );
  }

  /**
   * Build the data displayed on the timeline chart
   * @param snippetsData
   * @param snippetsList
   */
  public createUrlTimeLinealData(snippetsData: any, snippetsList: any[]): void {
    snippetsData.timeline.graph = [];

    if (snippetsList?.length > 0) {
      snippetsList.forEach((snippet) => {
        this.pushElementUrlTimeLineal(
          snippetsData.timeline,
          snippet,
          snippet.keyword,
          snippet.country
        );
      });
    }
  }

  /**
   * Build the object to be inserted into the array for the timeline
   * @param urlTimeLineal
   * @param snippet
   */
  private pushElementUrlTimeLineal(
    urlTimeLineal: any,
    snippet: any,
    keyword: string,
    countryTag: string
  ): void {
    const lineDescription = this.buildLineDescription(
      snippet,
      keyword,
      countryTag
    );
    if (snippet) {
      let element = {
        name: lineDescription,
        extra: { feeling: snippet.feeling, link: snippet.link },
        series: [],
      };
      // Date format '2022-05-05T16:32:48.62'
      snippet.tracking.forEach((track) => {
        const tooltipTrack =
          this.buildLineDescription(snippet, keyword, countryTag) +
          ': ' +
          track.position +
          ' (' +
          this.translate.translate.instant('label.pag') +
          ': ' +
          track.page +
          ' / ' +
          this.translate.translate.instant('label.pos') +
          ': ' +
          track.position_in_page +
          ')  ' +
          moment(track.date_scan).format('DD/MM/YYYY HH:mm:ss');

        element.series.push({
          name: new Date(track.date_scan),
          value: track.position,
          extra: {
            code: track.id,
            page: track.page,
            link: snippet.snippet.link,
          },
          tooltipText: tooltipTrack,
        });
      });

      switch (snippet.feeling) {
        case 'good':
          urlTimeLineal.schemaColors.domain.push(GREEN);
          break;
        case 'bad':
          urlTimeLineal.schemaColors.domain.push(RED);
          break;
        case 'neutral':
          urlTimeLineal.schemaColors.domain.push(BLUE);
          break;
        case 'not_applied':
          urlTimeLineal.schemaColors.domain.push(GRAY);
          break;
      }

      urlTimeLineal.graph.push(element);
    }
  }

  /**
   * Build new dashboard V2.0
   * @param scannerType
   * @param filteredResults
   * @param scanner
   * @returns
   */
  public buildV2(
    scannerType: string,
    filteredResults: any[],
    scanner: any,
    filters: any
  ): ScannerDashboardV2 {
    if (scannerType === 'one_shot') {
      return this.buildForOneShotV2(filteredResults, scanner, filters);
    } else if (scannerType === 'monitor') {
      return this.buildForRecurrentV2(filteredResults, scanner, filters);
    } else if (scannerType === 'transform') {
      return this.buildForTransformV2(filteredResults, scanner, filters);
    }

    return null;
  }

  /**
   * Basic Dashboard for OneShots
   * @param filteredResults
   * @returns
   */
  private buildForOneShotV2(
    filteredResults: any[],
    scanner: any,
    filters: any
  ): ScannerDashboardV2 {
    const data = this.buildBasicV2(filteredResults, filters);

    if (data) {
      this.buildPrincipalData(data, filteredResults, scanner, filters);
    }

    return data;
  }

  /**
   * Build Dashboard for Recurrents
   * @param filteredResults
   * @returns
   */
  private buildForRecurrentV2(
    filteredResults: any[],
    scanner: any,
    filters: any
  ): ScannerDashboardV2 {
    const data = this.buildForOneShotV2(filteredResults, scanner, filters);

    if (data) {
      this.buildNewContentTableV2(data, filteredResults, scanner, filters);
    }

    return data;
  }

  /**
   * Build Dashboard for Transform
   * @param filteredResults
   * @param scanner
   * @param filters
   * @private
   */
  private buildForTransformV2(
    filteredResults: any[],
    scanner: any,
    filters: any
  ): ScannerDashboardV2 {
    const data = this.buildForRecurrentV2(filteredResults, scanner, filters);

    if (data) {
      this.buildTableKeywordsByImpulses(
        data,
        filteredResults,
        scanner,
        filters
      );
    }

    return data;
  }

  /**
   * Create the new found news listing array
   * @param data
   * @param filteredResults
   * @param scanner
   * @param filters
   */
  buildNewContentTableV2(
    data: ScannerDashboardV2,
    filteredResults: any[],
    scanner: any,
    filters: any
  ) {
    if (filteredResults) {
      const firstQueryDate = moment(scanner.creation_date).clone();

      filteredResults.forEach((res) => {
        const lastQueryDate = moment(res.query_date).clone();

        let firstReferenceQueryDate = lastQueryDate
          .clone()
          .subtract(1, 'months');

        firstReferenceQueryDate = filters?.init_date
          ? filters.init_date
          : firstQueryDate.isBefore(firstReferenceQueryDate, 'day')
          ? firstReferenceQueryDate
          : firstQueryDate;

        const lastCompareDate = filters?.end_date
          ? moment(filters.end_date)
          : moment(res.query_date);

        res.snippetsImages.forEach((imgSnippet) => {
          if (
            !imgSnippet.feelingObj.NOT_APPLIED &&
            this.isNewContent(imgSnippet, firstReferenceQueryDate)
          ) {
            this.pushAlertContent(
              res,
              imgSnippet,
              data.alerts.new,
              lastCompareDate
            );
          }
        });

        res.snippetsNews.forEach((newsSnippet) => {
          if (
            !newsSnippet.feelingObj.NOT_APPLIED &&
            this.isNewContent(newsSnippet, firstReferenceQueryDate)
          ) {
            this.pushAlertContent(
              res,
              newsSnippet,
              data.alerts.new,
              lastCompareDate
            );
          }
        });

        res.snippetsWebs.forEach((webSnippet) => {
          if (
            !webSnippet.feelingObj.NOT_APPLIED &&
            this.isNewContent(webSnippet, firstReferenceQueryDate)
          ) {
            this.pushAlertContent(
              res,
              webSnippet,
              data.alerts.new,
              lastCompareDate
            );
          }
        });
      });

      return data;
    }

    return null;
  }

  /**
   * Check if the content is new or not
   * @param snippet
   * @param firstReferenceQueryDate
   */
  isNewContent(snippet: any, firstReferenceQueryDate: any): boolean {
    if (snippet?.tracking?.length > 0) {
      // Check if there is a tracking in the array
      const firstTimeFound = moment(snippet.tracking[0].date_scan).clone();

      if (firstTimeFound.isAfter(firstReferenceQueryDate, 'day')) {
        // Check if snippet is found after first date reference
        return true;
      }
    }
    return false;
  }

  /**
   *
   * @param filteredResults Build basic info for dashboard (Keywords por tema de rastreo, Keywords por URL)
   * @returns
   */
  private buildBasicV2(
    filteredResults: any[],
    filters?: any
  ): ScannerDashboardV2 {
    if (filteredResults) {
      const data = new ScannerDashboardV2();
      data.alerts = {
        negative: [],
        new: [],
      };

      const totals = {};

      filteredResults.forEach((res) => {
        res.snippetsImages.forEach((imgSnippet) => {
          const lastCompareDate = filters?.end_date
            ? moment(filters.end_date)
            : moment(res.query_date);

          this.checkAndPushResultsByTrackingWordsV2(
            data,
            imgSnippet,
            'keywordsByTW',
            'WORD',
            res.search_type,
            res.keyword.word,
            lastCompareDate,
            res.country
          );
          this.checkAndPushResultsByTrackingWordsV2(
            data,
            imgSnippet,
            'keywordsByURL',
            'URL',
            res.search_type,
            res.keyword.word,
            lastCompareDate,
            res.country
          );

          this.countByFeelingV2(res.keyword.word, imgSnippet.feeling, totals);
          if (imgSnippet.feeling === 'bad') {
            this.pushAlertContent(
              res,
              imgSnippet,
              data.alerts.negative,
              lastCompareDate
            );
          }
        });

        res.snippetsNews.forEach((newsSnippet) => {
          const lastCompareDate = filters?.end_date
            ? moment(filters.end_date)
            : moment(res.query_date);

          this.checkAndPushResultsByTrackingWordsV2(
            data,
            newsSnippet,
            'keywordsByTW',
            'WORD',
            res.search_type,
            res.keyword.word,
            lastCompareDate,
            res.country
          );
          this.checkAndPushResultsByTrackingWordsV2(
            data,
            newsSnippet,
            'keywordsByURL',
            'URL',
            res.search_type,
            res.keyword.word,
            lastCompareDate,
            res.country
          );

          this.countByFeelingV2(res.keyword.word, newsSnippet.feeling, totals);
          if (newsSnippet.feeling === 'bad') {
            this.pushAlertContent(
              res,
              newsSnippet,
              data.alerts.negative,
              lastCompareDate
            );
          }
        });

        res.snippetsWebs.forEach((webSnippet) => {
          const lastCompareDate = filters?.end_date
            ? moment(filters.end_date)
            : moment(res.query_date);

          this.checkAndPushResultsByTrackingWordsV2(
            data,
            webSnippet,
            'keywordsByTW',
            'WORD',
            res.search_type,
            res.keyword.word,
            lastCompareDate,
            res.country
          );
          this.checkAndPushResultsByTrackingWordsV2(
            data,
            webSnippet,
            'keywordsByURL',
            'URL',
            res.search_type,
            res.keyword.word,
            lastCompareDate,
            res.country
          );

          this.countByFeelingV2(res.keyword.word, webSnippet.feeling, totals);
          if (webSnippet.feeling === 'bad') {
            this.pushAlertContent(
              res,
              webSnippet,
              data.alerts.negative,
              lastCompareDate
            );
          }
        });
      });

      this.buildFeelingGraphsV2(data, totals);

      return data;
    }

    return null;
  }

  checkDays(first, second) {
    return (
      moment(first).format('DD/MM/YYYY') === moment(second).format('DD/MM/YYYY')
    );
  }
  /**
   * Creates the object that makes up the content array for table sections
   * @param result
   * @param snippet
   * @param dataList
   * @param lastDate
   */
  pushAlertContent(result: any, snippet: any, dataList: any[], lastDate: any) {
    dataList.push({
      keyword: result.keyword,
      trackingWords: snippet.trackingWords,
      typeSearch: result.search_type,
      rating_visualization: null,
      rating_reputation: null,
      country: result.country,
      snippet: snippet.snippet,
      link: snippet.link,
      display_link: snippet.displayed_link,
      domain: snippet.domain,
      tittle: snippet.title,
      trend:
        snippet.tracking[0].position ==
        snippet.tracking[snippet.tracking.length - 1].position
          ? 'SAME'
          : snippet.tracking[0].position <
            snippet.tracking[snippet.tracking.length - 1].position
          ? 'DOWN'
          : 'UP',
      page:
        snippet?.tracking?.length > 0
          ? snippet.tracking[snippet.tracking.length - 1].page
          : null,
      position:
        snippet?.tracking?.length > 0
          ? snippet.tracking[snippet.tracking.length - 1].position
          : null,
      position_in_page:
        snippet?.tracking?.length > 0
          ? snippet.tracking[snippet.tracking.length - 1].position_in_page
          : null,
      firstTimeDate:
        snippet?.tracking?.length > 0 ? snippet.tracking[0].date_scan : null,
      lastTimeDate:
        snippet?.tracking?.length > 0
          ? snippet.tracking[snippet.tracking.length - 1].date_scan
          : null,
      firstScan: snippet.tracking[0],
      stillInResults: this.checkDays(
        lastDate,
        snippet.tracking[snippet.tracking.length - 1].date_scan
      ),
      /*lastDate.isSame(
        moment(snippet.tracking[snippet.tracking.length - 1].date_scan)
      ),*/
    });
  }

  /**
   *
   * @param keyword
   * @param feeling
   * @param totals
   */
  countByFeelingV2(keyword: string, feeling: string, totals: any) {
    if (!totals[keyword]) {
      totals[keyword] = {
        snippetGood: 0,
        snippetBad: 0,
        snippetNeutral: 0,
        snippetNotApplied: 0,
      };
    }

    if (feeling === 'good') {
      totals[keyword].snippetGood++;
    } else if (feeling === 'bad') {
      totals[keyword].snippetBad++;
    } else if (feeling === 'neutral') {
      totals[keyword].snippetNeutral++;
    } else if (feeling === 'not_applied') {
      totals[keyword].snippetNotApplied++;
    }
  }

  /**
   * Build Donut graph "Sentimientos asignados"
   * @param data
   * @param totals
   */
  buildFeelingGraphsV2(data: ScannerDashboardV2, totals: any) {
    if (totals) {
      const label_good = this.translate.translate.instant('feeling.' + 'good');
      const label_bad = this.translate.translate.instant('feeling.' + 'bad');
      const label_neutral = this.translate.translate.instant(
        'feeling.' + 'neutral'
      );

      data.graphs = {
        feelingPie: [
          {
            name: label_good,
            value: 0,
            extra: { code: 'good' },
          },
          {
            name: label_bad,
            value: 0,
            extra: { code: 'bad' },
          },
          {
            name: label_neutral,
            value: 0,
            extra: { code: 'neutral' },
          },
        ],
        keywordBar: [],
      };

      for (const key of Object.keys(totals)) {
        const item: IScannerGraph = {
          name: key,
          series: [
            {
              name: label_good,
              value: totals[key].snippetGood,
              extra: { code: '' },
            },
            {
              name: label_bad,
              value: totals[key].snippetBad,
              extra: { code: '' },
            },
            {
              name: label_neutral,
              value: totals[key].snippetNeutral,
              extra: { code: '' },
            },
          ],
        };
        data.graphs.keywordBar.push(item);

        data.graphs.feelingPie[0].value += totals[key].snippetGood;
        data.graphs.feelingPie[1].value += totals[key].snippetBad;
        data.graphs.feelingPie[2].value += totals[key].snippetNeutral;
      }
    }
  }

  /**
   * Create the object that conforms the content array for trackingword table sections
   * @param data
   * @param snippet
   * @param listKey
   * @param type
   * @param searchType
   * @param keyword
   * @param query_date
   * @param country
   * @private
   */
  private checkAndPushResultsByTrackingWordsV2(
    data: ScannerDashboardV2,
    snippet: any,
    listKey: string,
    type: string,
    searchType: string,
    keyword: string,
    query_date: any,
    country: any
  ) {
    if (!data[listKey]) {
      data[listKey] = [];
    }

    const found = this.hasTrackingWordOrUrl(snippet, type);

    if (found && found.length > 0) {
      const firstScan = snippet.tracking[0];
      const lastScan = snippet.tracking[snippet.tracking.length - 1];
      data[listKey].push({
        snippet: {
          title: snippet.title,
          link: snippet.link,
          position:
            snippet.tracking && snippet.tracking.length > 0
              ? snippet.tracking[snippet.tracking.length - 1]
              : null,
          words: found,
          searchType,
          keyword,
          firstScan,
          lastScan,
          trend:
            firstScan.position == lastScan.position
              ? 'SAME'
              : firstScan.position < lastScan.position
              ? 'DOWN'
              : 'UP',
          stillInResults: this.checkDays(query_date, lastScan.date_scan),
          /*query_date.isSame(moment(lastScan.date_scan), 'day'),*/
          snippet: snippet.snippet,
          domain: snippet.domain,
        },
        country: country.tag,
        keyword,
      });
    }
  }

  /**
   * Build part of transform info (Keyword por impulsos)
   * @param data
   * @param filteredResults
   * @param scanner
   */
  buildTableKeywordsByImpulses(
    data: ScannerDashboardV2,
    filteredResults: any[],
    scanner: any,
    filters: any
  ) {
    if (scanner?.impulses) {
      data.keywordsByImpulse = [];

      scanner.impulses.forEach((impulse) => {
        filteredResults.forEach((res) => {
          res.snippetsImages.forEach((imgSnippet) => {
            if (imgSnippet.link === impulse.real_publish_link) {
              const lastCompareDate = filters?.end_date
                ? moment(filters.end_date)
                : moment(res.query_date);
              this.pushResult(
                data,
                imgSnippet,
                'keywordsByImpulse',
                'WORD',
                res.search_type,
                res.keyword.word,
                lastCompareDate,
                res.country.tag
              );
            }
          });

          res.snippetsNews.forEach((newsSnippet) => {
            if (newsSnippet.link === impulse.real_publish_link) {
              const lastCompareDate = filters?.end_date
                ? moment(filters.end_date)
                : moment(res.query_date);
              this.pushResult(
                data,
                newsSnippet,
                'keywordsByImpulse',
                'WORD',
                res.search_type,
                res.keyword.word,
                lastCompareDate,
                res.country.tag
              );
            }
          });

          res.snippetsWebs.forEach((webSnippet) => {
            if (webSnippet.link === impulse.real_publish_link) {
              const lastCompareDate = filters?.end_date
                ? moment(filters.end_date)
                : moment(res.query_date);
              this.pushResult(
                data,
                webSnippet,
                'keywordsByImpulse',
                'WORD',
                res.search_type,
                res.keyword.word,
                lastCompareDate,
                res.country.tag
              );
            }
          });
        });
      });
    }
  }

  /**
   * Add the snippet object to display to an array
   * @param data
   * @param snippet
   * @param listKey
   * @param type
   * @param searchType
   * @param keyword
   * @param query_date
   * @param country
   * @param targetReached
   */
  pushResult(
    data: any,
    snippet: any,
    listKey: string,
    type: string,
    searchType: string,
    keyword: string,
    lastCompareDate: any,
    country: string,
    targetReached?: boolean
  ) {
    if (!data[listKey]) {
      data[listKey] = [];
    }

    const found = this.hasTrackingWordOrUrl(snippet, type);
    const firstScan = snippet.tracking[0];
    const lastScan = snippet.tracking[snippet.tracking.length - 1];
    data[listKey].push({
      snippet: {
        title: snippet.title,
        link: snippet.link,
        position:
          snippet.tracking && snippet.tracking.length > 0
            ? snippet.tracking[snippet.tracking.length - 1]
            : null,
        words: found,
        searchType,
        keyword,
        firstScan,
        lastScan,
        trend:
          firstScan.position == lastScan.position
            ? 'SAME'
            : firstScan.position < lastScan.position
            ? 'DOWN'
            : 'UP',
        stillInResults: this.checkDays(lastCompareDate, lastScan.date_scan),

        /*lastCompareDate.isSame(
          moment(lastScan.date_scan),
          'day'
        ),*/
        snippet: snippet.snippet,
        domain: snippet.domain,
      },
      tracking: snippet.tracking,
      keyword,
      country,
      targetReached,
    });
  }

  /**
   * Build principal data for Transform dashboard ("Contenido a anular", "URL a impulsar")
   * @param data
   * @param filteredResults
   * @param scanner
   * @param minPosition
   */
  buildPrincipalData(
    data: ScannerDashboardV2,
    filteredResults: any[],
    scanner: any,
    filters: any,
    minPosition?: number
  ) {
    // Build remove data
    if (scanner?.justTrackingURLs?.length > 0) {
      data.removeData = {};

      data.removeData.target = scanner.progress;
      data.removeData.results = [];
      data.removeData.timeline = {
        minYAxis: minPosition
          ? minPosition
          : this.MIN_POSITIONS_IN_URL_TIME_LINEAL,
        maxYAxis: this.MAX_POSITIONS_IN_URL_TIME_LINEAL,
        schemaColors: { domain: [] },
        graph: [],
      };
      // Build Content to remove (Results and Timeline)
      // SYMR-16: Se solicita cambiar la comparación sin protocolos  https, www. http en impulso y anulación
      scanner.justTrackingURLs.forEach((url) => {
        filteredResults.forEach((res) => {
          res.snippetsImages.forEach((imgSnippet) => {
            if (
              this.utilService.removeProtocolComplete(imgSnippet.link) ===
              this.utilService.removeProtocolComplete(url.word)
            ) {
              const lastScan =
                imgSnippet.tracking[imgSnippet.tracking.length - 1];
              const lastCompareDate = filters?.end_date
                ? moment(filters.end_date)
                : moment(res.query_date);
              const targetReached = !lastCompareDate.isSame(
                moment(lastScan.date_scan),
                'day'
              )
                ? true
                : lastScan?.page > scanner?.restrictions?.detail?.target_page;

              this.pushResult(
                data.removeData,
                imgSnippet,
                'results',
                'WORD',
                res.search_type,
                res.keyword.word,
                lastCompareDate,
                res.country.tag,
                targetReached
              );
            }
          });

          res.snippetsNews.forEach((newsSnippet) => {
            if (
              this.utilService.removeProtocolComplete(newsSnippet.link) ===
              this.utilService.removeProtocolComplete(url.word)
            ) {
              const lastScan =
                newsSnippet.tracking[newsSnippet.tracking.length - 1];
              const lastCompareDate = filters?.end_date
                ? moment(filters.end_date)
                : moment(res.query_date);
              const targetReached = lastCompareDate.isSame(
                moment(lastScan.date_scan),
                'day'
              )
                ? lastScan?.page > scanner?.restrictions?.detail?.target_page
                : true;

              this.pushResult(
                data.removeData,
                newsSnippet,
                'results',
                'WORD',
                res.search_type,
                res.keyword.word,
                lastCompareDate,
                res.country.tag,
                targetReached
              );
            }
          });

          res.snippetsWebs.forEach((webSnippet) => {
            if (
              this.utilService.removeProtocolComplete(webSnippet.link) ===
              this.utilService.removeProtocolComplete(url.word)
            ) {
              const lastScan =
                webSnippet.tracking[webSnippet.tracking.length - 1];
              const lastCompareDate = filters?.end_date
                ? moment(filters.end_date)
                : moment(res.query_date);
              const targetReached = lastCompareDate.isSame(
                moment(lastScan.date_scan),
                'day'
              )
                ? lastScan?.page > scanner?.restrictions?.detail?.target_page
                : true;

              this.pushResult(
                data.removeData,
                webSnippet,
                'results',
                'WORD',
                res.search_type,
                res.keyword.word,
                lastCompareDate,
                res.country.tag,
                targetReached
              );
            }
          });
        });
      });

      data.removeData.timeline.graph.sort(this.sortTimelineGraph);
    }

    // Build URL to Impulse (Result and Timeline)
    if (scanner?.impulses?.length > 0) {
      data.impulseData = {};
      data.impulseData.target = 0;
      data.impulseData.results = [];
      data.impulseData.timeline = {
        minYAxis: minPosition
          ? minPosition
          : this.MIN_POSITIONS_IN_URL_TIME_LINEAL,
        maxYAxis: this.MAX_POSITIONS_IN_URL_TIME_LINEAL,
        schemaColors: { domain: [] },
        graph: [],
      };

      // Build Results
      // SYMR-16: Se solicita cambiar la comparación sin protocolos  https, www. http en impulso y anulación
      scanner.impulses.forEach((impulse) => {
        filteredResults.forEach((res) => {
          res.snippetsImages.forEach((imgSnippet) => {
            if (
              this.utilService.removeProtocolComplete(imgSnippet.link) ===
              this.utilService.removeProtocolComplete(impulse.real_publish_link)
            ) {
              const lastCompareDate = filters?.end_date
                ? moment(filters.end_date)
                : moment(res.query_date);
              this.pushResult(
                data.impulseData,
                imgSnippet,
                'results',
                'WORD',
                res.search_type,
                res.keyword.word,
                lastCompareDate,
                res.country.tag,
                impulse.target_reached
              );
            }
          });

          res.snippetsNews.forEach((newsSnippet) => {
            if (
              this.utilService.removeProtocolComplete(newsSnippet.link) ===
              this.utilService.removeProtocolComplete(impulse.real_publish_link)
            ) {
              const lastCompareDate = filters?.end_date
                ? moment(filters.end_date)
                : moment(res.query_date);
              this.pushResult(
                data.impulseData,
                newsSnippet,
                'results',
                'WORD',
                res.search_type,
                res.keyword.word,
                lastCompareDate,
                res.country.tag,
                impulse.target_reached
              );
            }
          });

          res.snippetsWebs.forEach((webSnippet) => {
            if (
              this.utilService.removeProtocolComplete(webSnippet.link) ===
              this.utilService.removeProtocolComplete(impulse.real_publish_link)
            ) {
              const lastCompareDate = filters?.end_date
                ? moment(filters.end_date)
                : moment(res.query_date);
              this.pushResult(
                data.impulseData,
                webSnippet,
                'results',
                'WORD',
                res.search_type,
                res.keyword.word,
                lastCompareDate,
                res.country.tag,
                impulse.target_reached
              );
            }
          });
        });

        if (impulse.target_reached) {
          data.impulseData.target++;
        }
      });
    }
  }

  sortTimelineGraph(a: any, b: any): number {
    try {
      return (
        a.series[a.series.length - 1].value -
        b.series[b.series.length - 1].value
      );
    } catch (exception) {}

    return 0;
  }
}
