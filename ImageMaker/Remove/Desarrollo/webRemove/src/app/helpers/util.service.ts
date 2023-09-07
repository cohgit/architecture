import { Injectable } from '@angular/core';
import { ModalService } from './modal.service';
import { DatePipe, Location } from '@angular/common';
import { TranslateService } from '@ngx-translate/core';
import html2canvas from 'html2canvas';
import jsPDF from 'jspdf';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import * as XLSX from 'xlsx';
import { SessionService } from './session.service';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { TermsComponent } from '../pages/checkout-cart/new-account/terms/terms.component';
import { PoliticsModalComponent } from '../pages/site/politics-modal/politics-modal.component';
import { ContentModalComponent } from '../pages/site/content-modal/content-modal.component';

declare var $: any;

@Injectable({
  providedIn: 'root',
})
export class UtilService {
  source = '../../../../assets/img/flags/';
  flagWith = 18;
  flagHeight = 12;
  userInfo: any;
  curr = 'EUR';
  pagination = [5, 15, 35, 50];
  offset = new Date().getTimezoneOffset();
  tz = Intl.DateTimeFormat().resolvedOptions().timeZone;
  /**
   * Service that compiles methods or functionalities used in the system on a recurring basis by any component
   * (both admin and client)
   * @param modal
   * @param location
   * @param translate
   * @param datePipe
   * @param ngxLoader
   * @param session
   * @param router
   * @param dialog
   */
  constructor(
    private modal: ModalService,
    private location: Location,
    public translate: TranslateService,
    private datePipe: DatePipe,
    private ngxLoader: NgxUiLoaderService,
    public session: SessionService,
    private router: Router,
    private dialog: MatDialog
  ) {}

  /**
   * Display a notification message with a list of messages
   * @param msjs
   * @param tipo (Possible values: 'danger', 'info', 'success', 'warning')
   */
  showNotificationsList(msjs: string[], tipo: string) {
    if (msjs) {
      msjs.forEach((msj) => {
        this.showNotification(msj, tipo);
      });
    }
  }

  /**
   * Display a Notification Message
   * @param code_message
   * @param tipo (Possible values: 'danger', 'info', 'success', 'warning')
   */
  showNotification(code_message: string, tipo: string) {
    this.displayNotification(this.translate.instant(code_message), tipo);
  }

  /**
   * Display a Notification Message with dynamic content message
   * @param initial_code_message
   * @param code_message
   * @param tipo (Possible values: 'danger', 'info', 'success', 'warning')
   */
  showComplexNotification(
    initial_code_message: string,
    code_message: string,
    tipo: string
  ) {
    this.displayNotification(
      this.translate.instant(initial_code_message) +
        '(' +
        this.translate.instant(code_message) +
        ')',
      tipo
    );
  }

  /**
   * Display a Notification Message
   * @param message
   * @param tipo (Possible values: 'danger', 'info', 'success', 'warning')
   */
  private displayNotification(message: string, tipo: string) {
    let icono = 'fa fa-bullhorn';

    if (tipo === 'danger') {
      icono = 'fa fa-exclamation-triangle';
    } else if (tipo === 'info') {
      icono = 'las la-bell';
    } else if (tipo === 'success') {
      icono = 'fa fa-check';
    } else if (tipo === 'warning') {
      icono = 'fa fa-bullhorn';
    }

    const type = [tipo];

    $.notify(
      {
        icon: icono,
        message,
      },
      {
        type,
        timer: 4000,
        showProgressbar: false,
        placement: {
          from: 'top',
          align: 'left',
        },
        template:
          '<div data-notify="container" class="col-xl-4 col-lg-4 col-11 col-sm-4 col-md-4 alert alert-{0} alert-with-icon" ' +
          'role="alert">' +
          '<button mat-button  type="button" aria-hidden="true" class="close mat-button" data-notify="dismiss"> ' +
          ' <i class="fa fa-times pull-right paddingtop17"></i></button>' +
          '<i class="fa-2x heartbeat espacioDer15" data-notify="icon"></i> ' +
          '<span data-notify="title">{1}</span> ' +
          '<span data-notify="message">{2}</span>' +
          '<div class="progress" data-notify="progressbar">' +
          '<div class="progress-bar progress-bar-{0}" role="progressbar" aria-valuenow="0" aria-valuemin="0" ' +
          'aria-valuemax="100" style="width: 0%;"></div>' +
          '</div>' +
          '<a href="{3}" target="{4}" data-notify="url"></a>' +
          '</div>',
      }
    );
  }

  /**
   * Extract an element from an array given an pair key-value reference
   * @param array
   * @param key
   * @param value
   * @returns
   */
  extractElementFromArray(array: any[], key: string, value: any): any {
    let result = null;

    array.forEach((elem) => {
      if (elem[key] === value) {
        result = elem;
      }
    });

    return result;
  }

  /**
   * Extract a new array with attributes of an object array.
   * @param array
   * @param key
   * @returns
   */
  extractAttrArray(array: any[], key: string): any[] {
    const result = [];

    array.forEach((elem) => result.push(elem[key]));

    return result;
  }

  /**
   * Filter an array based on a key and its position
   * @param array
   * @param filterKey
   * @param filterValue
   * @returns
   */
  filterArray(array: any[], filterKey: string, filterValue: any): any[] {
    if (array && array.length > 0) {
      const newArray = [];

      array.forEach((elem) => {
        if (elem[filterKey] === filterValue) {
          newArray.push(elem);
        }
      });

      return newArray;
    }

    return array;
  }

  /**
   * Extract a subarray from an array given an pair key-value reference
   * @param array
   * @param filterKeys
   * @param filterValues
   * @returns
   */
  filterArrayOrConditions(
    array: any[],
    filterKeys: string[],
    filterValues: any[]
  ): any[] {
    if (array && array.length > 0 && filterKeys && filterKeys.length > 0) {
      const newArray = [];

      array.forEach((elem) => {
        filterKeys.forEach((key) => {
          filterValues.forEach((val) => {
            if (elem[key] === val) {
              newArray.push(elem);
            }
          });
        });
      });

      return newArray;
    }

    return array;
  }

  /**
   * Extract a subarray from an array given an attribute nullable
   * @param array
   * @param filterKey
   * @returns
   */
  filterArrayNotNullField(array: any[], filterKey: string): any[] {
    if (array && array.length > 0) {
      const newArray = [];

      array.forEach((elem) => {
        if (elem[filterKey] != null) {
          newArray.push(elem);
        }
      });

      return newArray;
    }

    return array;
  }

  /**
   * Compare if 'id' attribute of two objects are the same
   * @param f1
   * @param f2
   * @returns
   */
  compareById(f1: any, f2: any): boolean {
    return f1 && f2 && f1.id === f2.id;
  }

  /**
   * Compare if 'value' attribute of two objects are the same
   * @param c1
   * @param c2
   */
  compareValue(c1: any, c2: any): boolean {
    return c1 && c2 && c1 === c2;
  }

  /**
   * Compare if 'code' attribute of two objects are the same
   * @param f1
   * @param f2
   * @returns
   */
  compareByCode(f1: any, f2: any): boolean {
    return f1 && f2 && f1.code === f2.code;
  }

  /**
   * Compare if 'tag' attribute of two objects are the same
   * @param c1
   * @param c2
   */
  compareTag(c1: any, c2: any): boolean {
    return c1 && c2 && c1.tag === c2.tag;
  }

  /**
   * Extract an element of an array
   * @param input
   * @param arrayRecived
   */
  removefromArray(input: any, arrayRecived: any[]): void {
    const index = arrayRecived.indexOf(input);
    if (index >= 0) {
      arrayRecived.splice(index, 1);
    }
  }

  /**
   * Extract an element of an array by key
   * @param input
   * @param arrayRecived
   */
  removefromArrayByKey(arrayRecived: any[], input: any, key: string): void {
    arrayRecived.forEach((value, index) => {
      if (value[key] === input[key]) {
        arrayRecived.splice(index, 1);
      }
    });
  }

  /**
   * Check if an array has duplicated elements by attribute.
   * @param collection
   * @param attr
   * @param ignoreEmpty
   * @returns
   */
  checkDuplicatedInArray(
    collection: any[],
    attr: string,
    ignoreEmpty?: boolean
  ): { filtred: any[]; repeated: any[] } {
    const filtred = [];
    const repeated = [];

    collection.map(function (item) {
      const existItem = ignoreEmpty
        ? filtred.find((x) => x[attr] && x[attr] == item[attr])
        : filtred.find((x) => x[attr] == item[attr]);

      if (existItem) {
        repeated.push(item);
      } else {
        filtred.push(item);
      }
    });

    return { filtred, repeated };
  }

  /**
   * Allows you to activate or deactivate a toggle after confirmation
   * @param row
   * @param boolKey
   * @param nameKey
   * @param service
   * @param successFunction
   */
  toggleActivo(
    row: any,
    boolKey: string,
    nameKey: string,
    service: any,
    successFunction?: Function
  ) {
    if (row[boolKey]) {
      this.toggleConfirm(
        row,
        boolKey,
        this.translate.instant('title.active_confirm'),
        this.translate.instant('label.confirm_activate') +
          ' ' +
          row[nameKey] +
          '?',
        this.translate.instant('button.activate'),
        service,
        successFunction
      );
    } else {
      this.toggleConfirm(
        row,
        boolKey,
        this.translate.instant('title.deactive_confirm'),
        this.translate.instant('label.confirm_deactivate') +
          ' ' +
          row[nameKey] +
          '?',
        this.translate.instant('button.deactivate'),
        service,
        successFunction
      );
    }
  }

  /**
   * Run the toggleActivo confirmation
   * @param row
   * @param title
   * @param code_message
   * @param buttonText
   * @param service
   * @param successFunction
   */
  private toggleConfirm(
    row: any,
    boolKey: string,
    title: string,
    code_message: string,
    buttonText: string,
    service: any,
    successFunction?: Function
  ) {
    let confirmed = false;

    this.modal.openConfirmation({
      title,
      message: code_message,
      confirmButtonText: buttonText,
      onConfirm: () => {
        confirmed = true;
        service.delete(row.id, successFunction);
      },
      afterClosed: () => {
        if (!confirmed) {
          row[boolKey] = !row[boolKey];
        }
      },
    });
  }

  /**
   * Randomize array elements
   * @param array
   * @returns
   */
  suffleArray(array: any[]): any[] {
    let currentIndex = array.length,
      temporaryValue,
      randomIndex;

    while (0 !== currentIndex) {
      randomIndex = Math.floor(Math.random() * currentIndex);
      currentIndex -= 1;
      temporaryValue = array[currentIndex];
      array[currentIndex] = array[randomIndex];
      array[randomIndex] = temporaryValue;
    }

    return array;
  }

  /**
   * Allows to obtain a base64 of a file
   * @param file
   * @returns
   */
  getBase64(file: File) {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => resolve(reader.result);
      reader.onerror = (error) => reject(error);
    });
  }

  /**
   * Go back one position in navegation's history
   */
  back() {
    this.location.back();
  }

  /**
   * Go back to home
   */
  goHome() {
    this.userInfo = this.session
      ?.getUser()
      .permissions?.profile?.code.toLowerCase();
    this.router.navigate([this.userInfo + '/module/home']);
  }

  /**
   * get scanner tittle
   * @param data
   */
  checkTitleScanner(data) {
    let tittle = '';
    if (data === 'one_shot') {
      tittle = this.translate.instant('title.scanner_one');
    }
    if (data === 'monitor') {
      tittle = this.translate.instant('title.monitor');
    }
    if (data === 'transform') {
      tittle = this.translate.instant('title.transform');
    }
    return tittle;
  }

  /**
   * validates the pattern of an email to verify that it is correct
   * @param email
   * @returns
   */
  validateEmail(email) {
    const re =
      /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
  }

  /**
   * Create a deep clone from an object
   * @param objectToClone
   * @returns
   */
  cloneObject(objectToClone: any): any {
    return JSON.parse(JSON.stringify(objectToClone));
  }

  /*
   * Verifica que no existan caracteres especiales, ejemplo ñ
   * */
  checkCaracter(word: any) {
    const type = typeof word;
    if (type === 'string') {
      const tittles = 'ÃÀÁÄÂÈÉËÊÌÍÏÎÒÓÖÔÙÚÜÛãàáäâèéëêìíïîòóöôùúüûÑñÇç';
      const original = 'AAAAAEEEEIIIIOOOOUUUUaaaaaeeeeiiiioooouuuunncc';
      for (let i = 0; i < tittles.length; i++) {
        word = word
          .replace(tittles.charAt(i), original.charAt(i))
          .toLowerCase();
      }
      return word;
    }
  }

  /**
   * check if a field is a date
   * @param isoDate
   */
  checkDate(isoDate) {
    if (isNaN(Date.parse(isoDate))) {
      return false;
    } else {
      if (isoDate != new Date(isoDate).toISOString().substr(0, 10)) {
        return false;
      }
    }
    return true;
  }

  /**
   * recursive function
   * @param key
   * @param obj
   */
  recursive(key: string, obj: any) {
    if (obj === undefined || obj === null) {
      return obj;
    }
    const splitted = key.split('.');
    if (splitted.length === 1) {
      return obj[splitted[0]];
    } else {
      return this.recursive(
        key.substring(splitted[0].length + 1),
        obj[splitted[0]]
      );
    }
  }

  /**
   *
   * @param keysFilters
   * @param filtersValues
   * @param listToFilter
   *    * Realiza la función de filtrar un arreglo
   * */
  filterResult(keysFilters: any[], filtersValues: any, listToFilter: any[]) {
    let myArray = [];
    let objFilter = {};
    const exist = false;
    if (
      keysFilters.length !== 0 ||
      filtersValues ||
      listToFilter.length !== 0
    ) {
      myArray = listToFilter;
      keysFilters.forEach((p) => {
        objFilter = this.recursive(p, filtersValues);
        if (
          myArray.length !== 0 &&
          filtersValues[p] !== '' &&
          filtersValues[p]
        ) {
          myArray = listToFilter.filter(
            (a) => this.checkCaracter(a[p]) === this.checkCaracter(objFilter)
          );
        }
      });

      /* keysFilters.forEach(p => {
         // Verifica que el arreglo no esté vacío ya que algún parámetro anterior no encontró nada
         if (myArray.length !== 0){
           if (filtersValues[p] !== ''){
              // Verifica que está en el primer nivel
             if (!this.checkSymbol('.', p)) {
               myArray = listToFilter.filter(a =>
                 this.checkCaracter(a[p]) === this.checkCaracter(filtersValues[p]));
             } else{
               // Verifica que está a partir del segundo nivel buscar hacer esto dinámico
               const arrayParam = p.split('.');
               if (arrayParam.length === 2){
                 myArray = listToFilter.filter(a =>
                   this.checkCaracter(a[arrayParam[0]][arrayParam[1]]) === this.checkCaracter(filtersValues[p]));
               }
               if (arrayParam.length === 3){
                 myArray = listToFilter.filter(a =>
                   this.checkCaracter(a[arrayParam[0]][arrayParam[1]][arrayParam[2]]) === this.checkCaracter(filtersValues[p]));
               }
             }
           }
         }
       });*/
    }
    return myArray;
  }

  /**
   * sort a table column ascending or descending
   * @param colName
   * @param ckeck
   * @param tableArray
   * @param direct
   */
  sortTable(
    colName: string,
    check: boolean,
    tableArray: any[],
    direct: boolean
  ): { array: any[]; direction: boolean } {
    tableArray = tableArray.sort((a, b) => {
      return this.compare(a, b, colName, direct ? 1 : -1);
    });
    direct = !direct;

    const sorted = {
      array: tableArray,
      direction: direct,
    };
    return sorted;
  }

  /**
   * Compare values with each other (used for sorting)
   * @param a
   * @param b
   * @param colName
   * @param sortDirection
   * @private
   */
  private compare(
    a: any,
    b: any,
    colName: string,
    sortDirection: number
  ): number {
    const splited = colName.split('.');

    if (splited.length == 1) {
      return (
        (a[colName] < b[colName] ? 1 : a[colName] > b[colName] ? -1 : 0) *
        sortDirection
      );
    } else {
      return this.compare(
        a[splited[0]],
        b[splited[0]],
        colName.substring(colName.indexOf('.') + 1),
        sortDirection
      );
    }
  }

  /**
   * allows moving items in a paginated array to the first page
   * @param classPaginatorName
   */
  changeSelectedPageToFirst(classPaginatorName: string) {
    const pagination: HTMLElement = document.getElementsByClassName(
      classPaginatorName
    )[0] as HTMLElement;
    const firstUL = pagination.children[0] as HTMLElement;
    const firstPage = firstUL.children[2] as HTMLElement;
    const firstLink = firstPage.children[0] as HTMLElement;
    firstLink.click();
  }

  /**
   * * sort an array by value (number)
   * @param array
   * @param field
   */
  sortArrayValue(array: any, field: string) {
    array.sort(function (a, b) {
      return a[field] - b[field];
    });
    return array;
  }

  /**
   * sort an array by string (number)
   * @param array
   * @param field
   */
  sortArrayString(array: any, field: string) {
    array.sort((a, b) => a[field].localeCompare(b[field]));
  }

  /**
   * sort an array by date
   * @param array
   * @param field
   */
  sortArrayDate(array: any, field: string) {
    array.sort(
      (a, b) => new Date(b[field]).getTime() - new Date(b[field]).getTime()
    );
  }

  /**
   * Assign the flags to the countries according to the tag
   * @param tag
   */
  checkFlag(tag: string | number) {
    return this.source + tag + '.png';
  }

  /**
   * get only domain from  an URL
   * @param url
   */
  onlyDomain(url) {
    let domain;
    try {
      domain = new URL(url);
    } catch (_) {
      return url;
    }
    return domain.hostname.replace('www.', '');
  }

  /**
   * Remove This will take care of http, https and www
   * @param url
   */
  removeProtocolComplete(url) {
    // startsWith

    if (url.startsWith('www.')) {
      const www = 'www.';
      return url.slice(www.length);
    }

    if (url.startsWith('https://www.')) {
      const https = 'https://www.';
      return url.slice(https.length);
    }

    if (url.startsWith('http://www.')) {
      const http = 'http://www.';
      return url.slice(http.length);
    }

    if (url.startsWith('https://')) {
      const https = 'https://';
      return url.slice(https.length);
    }

    if (url.startsWith('http://')) {
      const http = 'http://';
      return url.slice(http.length);
    }

    // url is correct

    return url;
  }
  /**
   * validates the pattern of a URL to verify that it is correct
   * @param str
   */
  validURL(str) {
    const pattern = new RegExp(
      "^(http|https|ftp)://[a-zA-Z0-9-.]+.[a-zA-Z]{2,3}(:[a-zA-Z0-9]*)?/?([a-zA-Z0-9-._?,'/\\+&amp;%$#=~])*$",
      'i'
    ); // fragment locator
    return !!pattern.test(str);
  }

  /**
   * validates the pattern of a URL to verify that it is correct
   * @param str
   */
  checkSpaces(str) {
    return str.indexOf(' ') <= 0;
  }
  /**
   * Allows you to download an excel file of a list with an identifier
   * @param nameRecive
   */
  public downloadExcel(nameRecive: string) {
    let fileName = 'Export_Remove_';
    const table = document.getElementById('toExport');
    const ws: XLSX.WorkSheet = XLSX.utils.table_to_sheet(table, { raw: true });

    /* generate workbook and add the worksheet */
    const wb: XLSX.WorkBook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, 'Hoja1');

    /* save to file */
    if (nameRecive !== '') {
      fileName = this.translate.instant(nameRecive) + '-';
    }
    fileName += this.datePipe.transform(new Date(), 'yyyyMMdd_HHmm') + '.xlsx';

    XLSX.writeFile(wb, fileName);
  }

  /**
   * Allows you to download an excel file of a list with an identifier
   * @param nameRecive
   * @param data
   */
  public downloadDataToExcel(nameRecive: string, data: any[]) {
    let fileName = 'Export_Remove_';
    const table = document.getElementById('toExport');

    const wb = XLSX.utils.book_new(); // create a new blank book
    const ws = XLSX.utils.json_to_sheet(data);

    /* save to file */
    if (nameRecive !== '') {
      fileName = this.translate.instant(nameRecive) + '-';
    }

    XLSX.utils.book_append_sheet(wb, ws, 'Hoja1');
    fileName += this.datePipe.transform(new Date(), 'yyyyMMdd_HHmm') + '.xlsx';

    XLSX.writeFile(wb, fileName);
  }

  /**
   * Allows you to download a PDF file of a list with an identifier
   * @param nameRecive
   */
  public donwloadPDF(nameRecive: string): void {
    this.ngxLoader.start();
    const DATA = document.getElementById('toExport');
    let fileName = 'Export_Remove_';
    html2canvas(DATA, {
      scale: 2,
      allowTaint: false,
      useCORS: true,
      ignoreElements: (node) => {
        const stylesBefore = window.getComputedStyle(node, ':before');
        const stylesAfter = window.getComputedStyle(node, ':after');
        const stylesFirstLetter = window.getComputedStyle(
          node,
          ':first-letter'
        );
        const stylesFirstLine = window.getComputedStyle(node, ':first-line');
        const stylesSelection = window.getComputedStyle(node, ':selection');
        return (
          stylesBefore.backgroundImage.indexOf('.svg') !== -1 ||
          stylesAfter.backgroundImage.indexOf('.svg') !== -1 ||
          stylesFirstLetter.backgroundImage.indexOf('.svg') !== -1 ||
          stylesFirstLine.backgroundImage.indexOf('.svg') !== -1 ||
          stylesSelection.backgroundImage.indexOf('.svg') !== -1
        );
      },
    }).then((canvas) => {
      const imgData = canvas.toDataURL('image/jpeg');
      const imgWidth = 210;
      const pageHeight = 295;
      const imgHeight = (canvas.height * imgWidth) / canvas.width;
      let heightLeft = imgHeight;
      const doc = new jsPDF('p', 'mm', 'letter');
      let position = 10;
      doc.addImage(imgData, 'JPEG', 0, position, imgWidth, imgHeight);
      heightLeft -= pageHeight;

      while (heightLeft >= 0) {
        position = heightLeft - imgHeight;
        doc.addPage();
        const imgProps = doc.getImageProperties(imgData);
        const pdfWidth = doc.internal.pageSize.getWidth();
        const pdfHeight = (imgProps.height * pdfWidth) / imgProps.width;
        doc.addImage(imgData, 'JPEG', 0, position, pdfWidth, pdfHeight);
        heightLeft -= pageHeight;
      }
      if (nameRecive !== '') {
        fileName = this.translate.instant(nameRecive) + '-';
        //  fileName = this.translate.instant(name);
      }
      fileName += this.datePipe.transform(new Date(), 'yyyyMMdd_HHmm') + '.pdf';
      doc.save(fileName);
      this.ngxLoader.stop();
    });
  }

  /**
   *
   * @param lenghtArray
   * @param maxPage
   */
  showPaginator(lenghtArray: number, maxPage: number) {
    return lenghtArray > maxPage;
  }

  /**
   * Redirect to checkout (external url)
   */
  goPlans(): void {
    this.router.navigate(['/client/checkout']);
  }

  /**
   * Redirect to demo (external url)
   */
  goDemo(): void {
    this.router.navigate(['/client/site/demo']);
  }

  /**
   * Redirect to contact (external url)
   */
  goContact(): void {
    this.router.navigate(['/client/site/contact']);
  }

  /**
   * Redirect to the landing page (external url)
   */
  goHomeLanding(): void {
    this.router.navigate(['/client/site']);
  }

  /**
   * Redirect to FAQ (external url)
   */
  goFaq(): void {
    this.router.navigate(['/client/site/faq']);
  }

  /**
   * Redirect to cookies (external url)
   */
  goCookies(): void {
    this.router.navigate(['/client/site/cookies']);
  }

  /**
   * Redirect to client login (external url)
   */
  goToLogin(): void {
    this.router.navigate(['/client/login']);
  }

  /**
   *  function to open modal
   * @param data
   * @param componente
   */
  openModal(data: any, componente: any): void {
    const dialogRef = this.dialog.open(componente, {
      closeOnNavigation: true,
      disableClose: true,
      data: {
        info: data,
      },
    });
  }

  /**
   * Open a modal with the privacy policies
   *
   */
  openPolitics() {
    this.openModal(null, PoliticsModalComponent);
  }

  /**
   * Open a modal with the wordpress news
   * @param n
   */
  openNews(n: any) {
    this.openModal(n, ContentModalComponent);
  }

  /**
   * Open a modal with the terms and conditions
   */
  openTerms(): void {
    this.openModal(null, TermsComponent);
  }

  /**
   * Check if a word is contained in an array
   * @param text
   * @param word
   */
  verifyWordIn(text: string, word: string): boolean {
    let found = false;

    if (text && word) {
      text.split(' ').forEach((splitedWord) => {
        if (splitedWord.toUpperCase() === word.toUpperCase()) {
          found = true;
        }
      });
    }

    return found;
  }

  /**
   * Translate and order the items of an array (for example, countries)
   * @param array
   * @param translationKey
   * @param referenceAttr
   * @param transAttr
   */
  translateAndSort(
    array: any[],
    translationKey: string,
    referenceAttr: string,
    transAttr: string
  ): any[] {
    let sortedArray = [];

    if (array) {
      array.forEach((element) => {
        element[transAttr] = this.translate.instant(
          translationKey + element[referenceAttr]
        );
        sortedArray.push(element);
      });
    }

    sortedArray = sortedArray.sort((obj1, obj2) => {
      if (obj1[transAttr].toUpperCase() > obj2[transAttr].toUpperCase()) {
        return 1;
      }

      if (obj1[transAttr].toUpperCase() < obj2[transAttr].toUpperCase()) {
        return -1;
      }

      return 0;
    });

    return sortedArray;
  }

  /**
   *
   * @param list
   * @param field
   */
  toTitleCaseList(list: any[], field: string) {
    list?.forEach((elem) => {
      elem[field] = this.toTitleCase(elem[field]);
    });
  }

  /**
   *
   * @param str
   */
  toTitleCase(str: string) {
    if (str) {
      return str.replace(/\w\S*/g, function (txt) {
        return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();
      });
    }

    return str;
  }

  /**
   * Add protocol to a URL
   * @param url
   */
  addProtocolo(url) {
    if (url) {
      const route = url.toString();
      if (route.indexOf('http') === -1 || route.indexOf('https') === -1) {
        return 'https://' + url;
      }
    }
  }

  /**
   * Check if a URL starts with http or https
   * @param url
   */
  checkProtocolo(url) {
    if (url) {
      const route = url.toString().toUpperCase();
      if (route.indexOf('HTTP') === 0) {
        return true;
      }
      if (route.indexOf('HTTPS') === 0) {
        return true;
      } else {
        return false;
      }
    }
  }

  /**
   *
   * @param dt
   */
  formatDate(date) {
    /*const d = new Date(date);
    d.toISOString();
    d.setMinutes(d.getMinutes() - d.getTimezoneOffset());
    d.toISOString();
    return d;*/
    const tz = Intl.DateTimeFormat().resolvedOptions().timeZone;
    //console.log('tz', tz);
    const d = new Date(date);
    return d.toLocaleString('en-US', { timeZone: tz });

    /* const fdate = new Date(date);
    return new Date(Date.UTC(
      fdate.getFullYear(),
      fdate.getMonth(),
      fdate.getDate(),
      fdate.getHours(),
      fdate.getMinutes(),
      fdate.getSeconds()
    ));*/
  }

  /**
   * Elimina el contenido HTLM que pueda tener un string
   * @param data
   */
  cleanHTML(data: any) {
    const cleanData = data ? String(data).replace(/<[^>]+>/gm, '') : '';
    const noHtml = cleanData.replace(/&#.*?;/g, '');
    // console.log('ms', noHtml);
    return noHtml;
  }

  /**
   * Count how many words are contained in a text
   * @param text
   */
  countingWords(text: any) {
    const cleanData = text ? String(text).replace(/<[^>]+>/gm, '') : '';
    const noHtml = cleanData.replace(/&#.*?;/g, '');
    let counter = [];
    counter = noHtml.split(/\s+/);
    return counter.length;
  }

  /**
   * Count the characters in a text
   * @param text
   */
  countingCharacter(text: any) {
    const cleanData = text ? String(text).replace(/<[^>]+>/gm, '') : '';
    const noHtml = cleanData.replace(/&#.*?;/g, '');
    // console.log('ms', noHtml);
    return noHtml.length;
  }
  /**
   *
   * @param str clean a string only numbers o letters
   * @returns
   */
  cleanString(str: any) {
    return str.replace(/[^0-9a-zA-Z.]/g, '_');
  }
  /**
   * check if a string is only spaces
   * @param str
   */
  onlySpaces(str: any) {
    return !str.trim();
  }

  splitArray(myString) {
    let usingSplit = [];
    if (myString.length > 0) {
      usingSplit = myString.split(',');
      return usingSplit;
    }
  }
}
