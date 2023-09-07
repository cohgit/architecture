import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { CommonService } from '../../../../services/common.service';
import { NgForm } from '@angular/forms';
import { UtilService } from '../../../../helpers/util.service';
import { TranslateHelperService } from '../../../../helpers/translate-helper.service';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';

@Component({
  selector: 'app-add-item',
  templateUrl: './add-item.component.html',
  styleUrls: ['./add-item.component.css'],
})
export class AddItemComponent implements OnInit {
  info: any;
  trackingword: any = {};

  allFeelings: any;
  allWords: any;
  lstWords: any;
  isURL = false;
  lstItems: any = [];
  encontrado = false;
  isTransform = false;
  onsubmit = false;
  validWord = true;
  validFeeling = true;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private utilService: UtilService,
    private dialogRef: MatDialogRef<AddItemComponent>,
    private commonService: CommonService,
    public translateHelper: TranslateHelperService
  ) {
    this.info = data;
    if (this.info.plan === 'transform') {
      this.isTransform = true;
    }
  }

  ngOnInit(): void {
    this.lstItems = this.info.list;
    this.loadCommons();
    if (this.info.typeModal === 'URL') {
      this.isURL = true;
    }
  }

  /**
   * carga todos los servicios que permiten rellenar los selects de sentimientos
   */
  loadCommons(): void {
    this.commonService.listFeelingsIgnoreNotApply(
      (response) => (this.allFeelings = response)
    );
    this.commonService.listTrackingWords(
      (response) => (this.allWords = response)
    );
  }

  /**
   *
   * @param reference
   */
  doFilter(reference: string) {
    this.lstWords = this.allWords.filter((u) =>
      u.word.toUpperCase().includes(reference.toUpperCase())
    );
  }

  /**
   * Preselect feeling by recommended
   * @param $event
   */
  selectedWord($event: MatAutocompleteSelectedEvent) {
    const result = this.utilService.extractElementFromArray(
      this.allWords,
      'word',
      $event.option.value
    );
    if (result) {
      this.trackingword.feelingObj = result.feelingObj;
    }
  }

  /**
   * borrar los campos
   */
  cleanFiels(): void {
    this.trackingword = {};
  }

  /**
   *valida las URL o las tracking word a guardar
   * @param adItem
   */
  addItem(item, form: NgForm): void {
    this.validWord = true;
    this.validFeeling = true;
    this.onsubmit = true;
    let validURL = false;
    this.encontrado = false;

    if (this.isTransform && this.isURL) {
      console.log('this.isTransform && this.isURL');
      if (Object.keys(item).length !== 0 && item.word) {
        validURL = this.checkURL(item.word);
        if (validURL) {
          this.checkAndAdd(item);
        } else {
          this.utilService.showNotification('error.url_not_valid', 'danger');
        }
      } else {
        this.validWord = false;
      }
    } else {
      console.log('else 1');
      if (Object.keys(item).length !== 0 && item.word && item.feelingObj) {
        console.log(
          'Object.keys(item).length !== 0 && item.word && item.feelingObj'
        );
        if (this.isURL) {
          validURL = this.checkURL(item.word);
          if (validURL) {
            this.checkAndAdd(item);
          } else {
            this.utilService.showNotification('error.url_not_valid', 'danger');
          }
        } else {
          this.checkAndAdd(item);
        }
      } else {
        console.log('else 2');
        if (Object.keys(item).length === 0) {
          this.validWord = false;
          this.validFeeling = false;
        } else {
          if (!item.word) {
            this.validWord = false;
          }
          if (!item.feelingObj) {
            this.validFeeling = false;
          }
        }
      }
    }
  }

  /**
   * verifica si el item a agregar es transforma y URL y asigna los sentimientos negativos y luego carga la lista
   * @param obj
   */
  addAndCheckTransform(obj) {
    //TODO revisar esto a ver si de verdad debo asignarle el sentimiento por aca o debe ir vacio... vacio da error
    // de parámetros inválidos
    this.lstItems.push(obj);
    this.cleanFiels();
    // this.onsubmit = false;
    /* if (this.isTransform && this.isURL) {
       this.trackingword.feelingObj = {
         BAD: true,
         tag: 'bad'
       };
       this.lstItems.push(obj);
       this.cleanFiels();
       this.onsubmit = false;
     } else {
       this.lstItems.push(obj);
       this.cleanFiels();
       this.onsubmit = false;
     }*/
  }

  /**
   * verifica si se ha encontrado una URL o palabra en el lsitado o en las palabras prohibidas
   * @param item
   */
  checkAndAdd(item) {
    const forbiddenFound = this.isForbiddenWord();
    if (forbiddenFound.length === 0) {
      if (this.lstItems.length !== 0) {
        this.lstItems.forEach((obj) => {
          if (
            obj.word.toLowerCase() === item.word.toLowerCase()
            /* Deprecated SYMR-327 this.utilService.checkCaracter(obj.word) ===
            this.utilService.checkCaracter(item.word)*/
          ) {
            this.encontrado = true;
          }
        });
        if (!this.encontrado) {
          this.addAndCheckTransform(this.trackingword);
        }
      } else {
        this.addAndCheckTransform(this.trackingword);
      }
    } else {
      this.utilService.showNotification('message.forbbiden_words', 'danger');
    }
  }

  /**
   * verifica las palabras prohibidas
   */
  isForbiddenWord(): string[] {
    const found = [];

    if (this.info.forbiddenWords) {
      this.info.forbiddenWords.forEach((forbiddenWord) => {
        if (
          this.utilService.verifyWordIn(
            this.trackingword.word,
            forbiddenWord.word
          )
        ) {
          found.push(forbiddenWord.word);
        }
      });
    }

    return found;
  }

  /**
   * valida una URL
   * @param url
   */
  checkURL(url) {
    //  return this.utilService.validURL(url);
    if (url && url !== '') {
      // const isValid = this.utilService.validURL(url);
      const isValid = this.utilService.checkSpaces(url);
      const validProtocol = this.utilService.checkProtocolo(url);
      return !(!isValid || !validProtocol);
    }
  }

  /**
   *
   */
  close(): void {
    this.dialogRef.close(this.lstItems);
  }

  /**
   * elimina un registro de tabla
   * @param data
   */
  removeItem(data: any): void {
    if (this.info.idScanner) {
      this.lstItems.forEach((item) => {
        if (item === data) {
          data.markToDelete = !data.markToDelete;
        }
      });
    } else {
      this.lstItems.forEach((item) => {
        if (item === data) {
          const index = this.lstItems.findIndex((i) => i === data);
          this.lstItems.splice(index, 1);
        }
      });
    }

    /*  this.lstItems.forEach(item => {
        if (item === data) {
          const index = this.lstItems.findIndex(i => i === data);
          this.lstItems.splice(index, 1);
        }
      });*/
  }
}
