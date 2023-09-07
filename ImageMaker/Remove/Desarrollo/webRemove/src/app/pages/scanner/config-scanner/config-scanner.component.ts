import {
  Component,
  ElementRef,
  EventEmitter,
  Input,
  OnInit,
  Output,
  ViewChild,
} from '@angular/core';
import { MatChipInputEvent } from '@angular/material/chips';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { NgForm } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { AddItemComponent } from './add-item/add-item.component';
import { TranslateHelperService } from '../../../helpers/translate-helper.service';
import { UtilService } from '../../../helpers/util.service';
import { CommonService } from '../../../services/common.service';
import { TranslateService } from '@ngx-translate/core';
import { SessionService } from 'src/app/helpers/session.service';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { Observable } from 'rxjs';
import { ModalService } from 'src/app/helpers/modal.service';
import { ActivatedRoute } from '@angular/router';
import { ConfirmScannerComponent } from './confirm-scanner/confirm-scanner.component';

@Component({
  selector: 'app-config-scanner',
  templateUrl: './config-scanner.component.html',
  styleUrls: ['./config-scanner.css'],
})
export class ConfigScannerComponent implements OnInit {
  @Input() scannerInfo: any = {};

  @Output() generate = new EventEmitter<any>();
  @Output() updateConfiguration = new EventEmitter<any>();
  @ViewChild('fruitInput') fruitInput: ElementRef<HTMLInputElement>;

  endingButton = false;
  lstAllCountries: any;
  selectable = true;
  onBlur = true;
  showConfigAdvance = false;
  allDevices = [];
  allLanguages = [];
  allTypes = [];
  allColors = [];
  allSizes = [];
  allImgTypes = [];
  fltCountries = new Observable<any[]>();
  separatorKeysCodes: number[] = [ENTER, COMMA];
  placeholderBuscar = '';
  noResults = '';
  totalKeywordsSelected = 0;
  start = 2;
  disableNewKeywordsInputs = false;
  areSuggest = false;
  showResults = true;
  forbiddenWords: any[] = [];
  operation: string;
  compatible = true;

  // Parameters for disable form behavior
  isDisabledForm = true;
  isAdminAccess = false;

  @ViewChild('tmInput', { static: false })
  tmInput: ElementRef<HTMLInputElement>;

  constructor(
    private dialog: MatDialog,
    public translateHelper: TranslateHelperService,
    public utilService: UtilService,
    public commonService: CommonService,
    public translate: TranslateService,
    private session: SessionService,
    private ngxLoader: NgxUiLoaderService,
    private activatedRoute: ActivatedRoute,
    private modal: ModalService
  ) {}

  ngOnInit(): void {
    this.operation = this.activatedRoute.snapshot.paramMap.get('operation');
    this.loadCommons();
    this.checkSuggested();
    if (this.session.getAccessConfig().ADMIN) {
      this.isAdminAccess = true;
    }
  }
/**
 * Check if the form can be edited or not
 * @param scanner
 */
  markDisabledForm(scanner: any) {
    // isDisabledForm = true si no es nuevo y es operacion es de visualizar
    this.isDisabledForm = this.operation === 'view' && scanner.id != null;
  }
/**
 * remove keyword from array
 * @param input
 * @param arrayRecived
 */
  removeKeywords(input: any, arrayRecived: any[]) {
    this.scannerInfo?.keywords.forEach((key) => {
      if (key.word === input.word) {
        key.checked = false;
      }
    });
    this.utilService.removefromArray(input, arrayRecived);
    this.setTotalKeywordsSelected();
  }

  /**
   * remove from list an arrayRecived
   * @param input
   * @param arrayRecived
   */
  removeFromList(input: any, arrayRecived: any[]) {
    if (this.scannerInfo.id) {
      arrayRecived.forEach((item) => {
        if (item === input) {
          input.markToDelete = !input.markToDelete;
        }
      });
    } else {
      arrayRecived.forEach((item) => {
        if (item === input) {
          const index = arrayRecived.findIndex((i) => i === input);
          arrayRecived.splice(index, 1);
        }
      });
    }
    /*  this.scannerInfo?.keywords.forEach(key => {
        if (key.word === input.word) {
          key.checked = false;
        }
      });
      this.utilService.removefromArray(input, arrayRecived);
      this.setTotalKeywordsSelected();
  */
  }

  /**
   *
   * @param event
   * @param arrayRecived
   * @param fieldRecived
   * @param configForm
   * Agrega la keywods e invoca a la función que se encarga de buscar la sugeridas
   */
  addKeyword(
    event: MatChipInputEvent,
    arrayRecived: any[],
    fieldRecived: any,
    configForm: NgForm
  ): void {
    this.areSuggest = true;
    const input = event.input;
    const value = event.value;

    let keyword = { word: '', listSuggested: [], suggested: false };
    const forbiddenFound = this.isForbiddenWord(value.trim());

    if (forbiddenFound.length === 0) {
      if ((value || '').trim()) {
        keyword = { word: value.trim(), listSuggested: [], suggested: false };
        arrayRecived.push(keyword);
      }
      if (input) {
        input.value = '';
      }
      if (fieldRecived !== '') {
        fieldRecived.setValue(null);
      }
      this.setTotalKeywordsSelected();
      this.getSuggested(configForm);
    } else {
      this.utilService.showNotification('message.forbbiden_words', 'danger');
      console.error('Forbidden words found:', forbiddenFound);
    }
  }
/**
 *  check if a word is forbidden
 * @param keyword
 * @returns
 */
  isForbiddenWord(keyword: string) {
    const found = [];

    if (this.forbiddenWords && keyword) {
      this.forbiddenWords.forEach((forbiddenWord) => {
        if (this.utilService.verifyWordIn(keyword, forbiddenWord.word)) {
          found.push(forbiddenWord.word);
        }
      });
    }

    return found;
  }

  /**
   * open modal to edit a tracking word
   * @param inputType
   */
  manageTrackingWords(id) {
    this.manageWords('justTrackingWords', 'WORD', id);
  }

  /**
   * open modal to edit URL
   * @param inputType
   */
  manageUrlWords(id) {
    this.manageWords('justTrackingURLs', 'URL', id);
  }

  /**
   *
   * @param listWordKey
   * @param type
   * @param id
   * levanta el modal de agegar palabra clave o URL
   */
  private manageWords(listWordKey: string, type: string, id) {
    const dialogRef = this.dialog.open(AddItemComponent, {
      width: '60%',
      disableClose: true,
      data: {
        isDisabled: this.isDisabledForm,
        isAdmin: this.isAdminAccess,
        idScanner: this.scannerInfo.id,
        typeModal: type,
        list: Object.assign([], this.scannerInfo[listWordKey]),
        active: this.checkID(id),
        plan: this.scannerInfo.type,
        forbiddenWords: this.forbiddenWords,
      },
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.scannerInfo[listWordKey] = result;
      }
    });
  }

  /**
   *
   * @param configForm
   * Si el país cambia, verifica las sugeridas
   */
  countryChange(configForm) {
    this.checkDisableCountries();
    this.getSuggested(configForm);
  }

  /**
   * bloquea los pasises según las restricciones
   */
  checkDisableCountries(): void {
    if (this.scannerInfo?.configuration && this.lstAllCountries) {
      if (
        this.scannerInfo.configuration.countries.length >=
        this.scannerInfo?.restrictions?.detail.max_countries
      ) {
        this.lstAllCountries.forEach((country) => {
          country.active =
            this.utilService.extractElementFromArray(
              this.scannerInfo.configuration.countries,
              'id',
              country.id
            ) != null;
        });
      } else {
        this.lstAllCountries?.forEach((country) => {
          country.active = true;
        });
      }
    }
  }

  /**
   * Carga los selects de configuración avanzada
   */
  loadCommons() {
    this.commonService.listDevices((response) => (this.allDevices = response));
    this.commonService.listLanguages(
      (response) => (this.allLanguages = response)
    );
    this.commonService.listImagesColours(
      (response) => (this.allColors = response)
    );
    this.commonService.listImagesSizes(
      (response) => (this.allSizes = response)
    );
    this.commonService.listImagesTypes(
      (response) => (this.allImgTypes = response)
    );
    this.commonService.listForbiddenWords((response) => {
      this.forbiddenWords = response;
    });
    /*this.commonService.listSearchTypes(response => {
      if (response) {
        response.forEach(type => {
          if (this.scannerInfo.restrictions.detail.sectionsToSearch.includes(type.code)) {
            this.allTypes.push(type);
          }
        });
      }
    });*/
    this.commonService.listCountries((response) => {
      this.lstAllCountries = response;
      this.fltCountries = this.lstAllCountries.slice();
      this.checkDisableCountries();
    });
  }

  /**
   * show or hide advance configuration
   * */
  showadvance(): void {
    this.showConfigAdvance = !this.showConfigAdvance;
  }

  /**
   * genera la cantidad de pag a buscar desde el 2 hasta el número de pag según la restricción
   * @param max
   */
  counter(max: number) {
    const quantities = [];
    for (let i = 2; i <= max; i++) {
      quantities.push(i);
    }
    return quantities;
  }

  /**
   * invoca al servicio de serp para generar los resultados
   * @param configForm
   */
  generateResultsScanner(configForm: NgForm): void {
    const emptyURL = false;
    if (configForm.valid) {
      if (this.compatible) {
        if (!emptyURL) {
          const dialogRef = this.dialog.open(ConfirmScannerComponent, {
            width: '60%',
            disableClose: true,
            data: {
              info: this.scannerInfo,
              isNew: true,
              tittle: 'message.confirmation.scanner.generation',
            },
          });
          dialogRef.afterClosed().subscribe((result) => {
            if (result && result?.close) {
              if (configForm.value.countries != null) {
                this.scannerInfo.configuration.countries =
                  configForm.value.countries;
              }
              this.generate.emit(this.scannerInfo);
            }
          });
        }
      } else {
        this.utilService.showNotification('message.section_device', 'danger');
      }
    } else {
      this.utilService.showNotification('error.incomplete.form', 'danger');
    }
  }

  /**
   * actualiza la configuración
   * @param configForm
   */
  updateConfigForRecurrents(configForm: NgForm): void {
    console.log('updateConfigForRecurrents');
    this.endingButton = true;
    this.scannerInfo.configuration.countries = configForm.value.countries;
    this.updateConfiguration.emit(this.scannerInfo);
  }

  // Deprecado
  setTotalKeywordsSelected(_scanner?: any): void {
    this.disableNewKeywordsInputs =
      this.scannerInfo?.keywords?.length >=
      this.scannerInfo?.restrictions?.detail?.max_keywords;
    /*this.showResults = true;
    if (this.scannerInfo?.keywords) {
      this.totalKeywordsSelected = this.scannerInfo.keywords.length;
      this.scannerInfo.keywords.forEach((keyword) => {
        if (keyword.listSuggested) {
          keyword.listSuggested.forEach((sug) => {
            if (sug.checked) {
              this.totalKeywordsSelected++;
            }
          });
        }
      });
    } else {
      this.totalKeywordsSelected = 0;
    }
    this.disableNewKeywordsInputs = this.totalKeywordsSelected > this.scannerInfo?.restrictions?.detail?.max_keywords;

    if (this.disableNewKeywordsInputs) {
      this.showResults = false;
      this.utilService.showNotification('message.restrictions', 'danger');
    }
    */
  }
/**
 * add a keyword from suggestes section
 * @param keyword
 * @param configForm
 */
  addRemoveSuggestedKeyword(keyword: any, configForm: any) {
    let found = false;
    let index;

    this.scannerInfo.keywords.forEach((k, i) => {
      if (k.word === keyword.word) {
        found = true;
        index = i;
      }
    });

    if (keyword.checked && !found) {
      this.scannerInfo.keywords.push(keyword);
      this.getSuggested(configForm);
    }
    if (!keyword.checked && found) {
      this.scannerInfo.keywords.splice(index, 1);
    }

    this.disableNewKeywordsInputs =
      this.scannerInfo.keywords.length >=
      this.scannerInfo?.restrictions?.detail?.max_keywords;

    this.getSuggested(configForm);
  }

  /**
   * Verifica si en base al id el campo esta habilitado o deshabilitado (Deprecado)
   * @param id
   */
  checkID(id): boolean {
    return id !== null;
  }

  /**
   * Verifica si existe alguna palabra sugerida seleccionada
   */
  checkSuggested(): void {
    this.scannerInfo?.keywords.forEach((key) => {
      if (key.listSuggested.length !== 0) {
        this.areSuggest = true;
      }
    });
  }

  /**
   * Invoca al servicio de google para consultar las sugeridas
   * @param configForm
   */

  getSuggested(configForm: NgForm): void {
    //  console.log('getsuggested', this.scannerInfo.configuration.language);
    // const lang = configForm.value.language;
    const lang = this.scannerInfo.configuration.language;
    let val: any;
    let encontrado = false;
    const pos = this.scannerInfo?.keywords.length - 1;
    if (this.scannerInfo?.keywords[pos]) {
      this.scannerInfo.keywords[pos].listSuggested = this.scannerInfo.keywords[
        pos
      ].listSuggested
        ? this.scannerInfo.keywords[pos].listSuggested
        : [];
    }

    this.scannerInfo?.keywords.forEach((key) => {
      this.scannerInfo?.configuration.countries?.forEach((country) => {
        encontrado = false;
        key.listSuggested?.forEach((insideCountry) => {
          if (insideCountry.countryObj.id === country.id) {
            encontrado = true;
          }
        });
        if (!encontrado) {
          this.commonService.googleSuggest(
            key.word,
            lang,
            country.tag,
            (result) => {
              if (result) {
                result.forEach((sug) => {
                  val = {
                    word: sug,
                    countryObj: country,
                  };
                  key.listSuggested.push(val);
                });
              }
              this.scannerInfo.keywords[pos].listSuggested =
                this.scannerInfo.keywords[pos].listSuggested.filter(
                  (v, i, a) =>
                    a.findIndex(
                      (v2) => JSON.stringify(v2) === JSON.stringify(v)
                    ) === i
                );
            }
          );
        }
      });
    });
  }
/**
 *
 * @param lstSearchTypes
 * @param device
 */
  checkCondition(lstSearchTypes: any, device: any) {
    this.compatible = true;
    if (device === 'tablet' || device === 'mobile') {
      if (lstSearchTypes.length !== 0) {
        lstSearchTypes.forEach((type) => {
          if (type === 'images' || type === 'news') {
            this.compatible = false;
          }
        });
      }
    }
  }
}
