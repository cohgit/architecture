import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { UtilService } from '../../../helpers/util.service';
import { ActivatedRoute } from '@angular/router';
import { MatChipInputEvent } from '@angular/material/chips';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { TranslateHelperService } from '../../../helpers/translate-helper.service';
import { DeindexationService } from 'src/app/services/deindexation.service';
import { DynamicFormHelperService } from 'src/app/helpers/dynamic-form-helper.service';
import { ModalService } from 'src/app/helpers/modal.service';
import { DynamicFormComponent } from 'src/app/shared/dynamic-form/dynamic-form.component';
import { UploaderFileService } from 'src/app/helpers/uploader-file.service';


@Component({
  selector: 'app-new-edit-deindexing',
  templateUrl: './new-edit-deindexing.component.html',
  styleUrls: ['./new-edit-deindexing.component.css']
})
export class NewEditDeindexingComponent implements OnInit {
  @ViewChild(DynamicFormComponent) dfc: DynamicFormComponent;
  disabled: boolean = true;

  private SPLITTER_KEYWORDS = '/';

  uuidDeindeindex: string;
  uuidClient: string;

  isEditable = false;
  endingButton = false;
  encontradoRepo = false;
  selectable = true;
  removable = true;
  separatorKeysCodes: number[] = [ENTER, COMMA];
  onBlur = true;
  labelFile: any;
  fileName = '';
  keywords: string[] = [];
  urlDeindexing: any = {};
  proof: any;
  file_types = [
    {
      type: 'URL',
    },
    {
      type: 'FILE',
    },
  ];
  deindexation: any = {};
  encontrado = false;
  fileType = 'application/pdf';
  file: any;

  // This params belong to url to deindex (Future refactore issue?)
  ask_google: boolean = true;
  ask_media: boolean = false;

  /**
   * manage a new deindexing or edit one
   * @param utilService
   * @param route
   * @param translate
   * @param deindexationService
   * @param dfh
   * @param modal
   * @param ufs
   */
  constructor(
    private utilService: UtilService,
    private route: ActivatedRoute,
    private translate: TranslateHelperService,
    private deindexationService: DeindexationService,
    public dfh: DynamicFormHelperService,
    private modal: ModalService,
    public ufs: UploaderFileService
  ) {
    this.uuidDeindeindex =
      this.route.snapshot.paramMap.get('uuid_deindexation');
    this.uuidClient = this.route.snapshot.paramMap.get('uuid_client');
  }

  ngOnInit(): void {
    this.cleanProofAtt();
    this.labelFile = this.translate.instant('label.load_field');
    this.loadDeindexation();

  }
removableKeyword(){
  if (this.deindexation.status && this.deindexation.status !== 'created'){
    this.removable = false;
  }
}
  /**
   * load the deindexidation info
   * @private
   */
  private loadDeindexation() {
    this.deindexationService.get(
      this.uuidDeindeindex,
      this.uuidClient,
      (response) => {
        this.deindexation = response;
        this.checkDisableForm();
        this.splitKeywords();
        this.setAskAttributes();
        this.dfh.initializeForm(this.deindexation.dynamicForm);
        this.removableKeyword();
      }
    );

  }

  /**
   * Temp method for inner atts
   */
  private setAskAttributes(): void {
    this.ask_google = this.deindexation?.urls?.length > 0 ? this.deindexation.urls[0].ask_google : true;
    this.ask_media = this.deindexation?.urls?.length > 0 ? this.deindexation.urls[0].ask_media : false;
  }
  /**
   * Temp method for inner atts
   */
  private getAskAttributes() {
    this.deindexation?.urls?.forEach(url => {
      url.ask_google = this.ask_google;
      url.ask_media = this.ask_media;
    })
  }

  /**
   * Collects the information from the input file
   * @param event
   */
  onFileSelected(event) {
    const file: File = event.target.files[0];

    if (file) {
      this.fileName = file.name;
      const formData = new FormData();
      formData.append('thumbnail', file);
      this.proof.content = file;
    }
    /**this.proof.content = {
      lastModified: file.lastModified,
      name: file.name,
      size: file.size,
      type: file.type,
    };**/
  }

  /**
   * add a keywords
   * @param event
   */
  add(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();
    const input = event.input;
    if (value) {
      this.keywords.push(value);
    }
    if (input) {
      input.value = '';
    }
  }

  /**
   * remove a keyword
   * @param word
   */
  remove(word): void {
    // console.log('this.keyword',  this.deindexation);
    const index = this.keywords.indexOf(word);
    if (index >= 0) {
      this.keywords.splice(index, 1);
    }
    /*if (this.deindexation.url_to_deindex_keywords){
      const index = this.deindexation.url_to_deindex_keywords.indexOf(word);
      if (index >= 0) {
        this.deindexation.url_to_deindex_keywords.splice(index, 1);
      }
    } else{
      const index = this.keywords.indexOf(word);
      if (index >= 0) {
        this.keywords.splice(index, 1);
      }
    }*/

  }

  /**
   * save or update information
   * @param form
   */
  onConfirmClick(form: NgForm): void {
    this.endingButton = true;
    if (form.valid) {
      this.joinKeywords();
      this.getAskAttributes();
      if (this.deindexation.id === null) {
        if (!this.deindexation.id) {
          this.deindexationService.save(this.deindexation, (response) => {
            this.deindexation = response;
            this.checkDisableForm();
            this.back();
          });
        }
      } else {
        this.joinKeywords();
        this.deindexationService.update(this.deindexation, (response) => {
          response?.evidenceFiles.forEach((file, index) => {
            if (file.markForDelete) {
              response.evidenceFiles.splice(index, 1);
            }
          })
          response?.urls.forEach((url, index) => {
            if (url.markToDelete) {
              response.urls.splice(index, 1);
            }
          });
          this.deindexation = response;
        });
      }
    } else {
      this.utilService.showNotification('error.required_fields', 'danger');
    }
  }

  /**
   * cleanProofAtt fields after add a proof
   */
  private cleanProofAtt(): void {
    this.proof = {
      content: {
        lastModified: 0,
        name: '',
        size: 0,
        type: '',
      },
      file_address: '',
      file_type: '',
      file_description: '',
    };
  }

  /**
   * check if an url is valid
   * @param url
   */
  checkURL(url) {
    return this.utilService.validURL(url);
  }

  /**
   * upload a proof
   * @param $event
   * @param proof
   */
  checkFile($event: any, proof: any): void {
    this.proof.file_address = $event;
    this.addProof(proof);
  }

  /**
   * add a proof to complete the table and push to the static form of an array
   * @param proof
   */
  addProof(proof: any) {
    this.encontradoRepo = false;

    switch (proof.file_type) {
      case 'URL': {
        if (
          proof.file_address !== '' &&
          this.checkURL(proof.file_address)
        ) {
          this.deindexation.evidenceFiles.forEach((obj) => {
            if (
              this.utilService.checkCaracter(obj.file_address) ===
              this.utilService.checkCaracter(proof.file_address)
            ) {
              this.encontradoRepo = true;
            }
          });
          if (!this.encontradoRepo) {
            this.deindexation.evidenceFiles.push(proof);
            this.cleanProofAtt();
          }
        }
        break;
      }
      case 'FILE': {
        this.deindexation.evidenceFiles.push(proof);
        this.cleanProofAtt();

        break;
      }
      default: {
        console.error('Error: File type not supported...');
        this.cleanProofAtt();
        break;
      }
    }
  }

  /**
   * remove a proof of the table and slice the static form array
   * @param data
   */
  removeProof(data: any) {
    data.markForDelete = !data.markForDelete;
  }

  /**
   * ask for a confirmation and send a deindexation
   * @param form
   */
  send(form: NgForm): void {
    if (form.valid && this.dfc.isValid()) {
      this.modal.openConfirmation({
        message: 'message.send_deindex',
        onConfirm: () => {
          this.getAskAttributes();
          if (this.deindexation.urls.length !== 0) {
            this.deindexationService.update(this.deindexation, (response) => {
              this.deindexation = response;
              this.checkDisableForm();
              this.deindexationService.markAsProcessing(this.deindexation.id, () => {
                this.loadDeindexation();
                this.back();
              });
            });
          } else {
            this.utilService.showNotification('message.error.url_deindex', 'danger');
          }
        },
      });
    } else {
      this.utilService.showNotification('error.required_fields', 'danger');
    }

  }

  /**
   * get a keywords array and splited at the begging
   */
  splitKeywords() {
    if (this.deindexation?.url_to_deindex_keywords?.length > 0) {
      this.keywords = this.deindexation.url_to_deindex_keywords.split(
        this.SPLITTER_KEYWORDS
      );
    }
  }

  /**
   * join keywords before save
   */
  joinKeywords() {
    this.deindexation.url_to_deindex_keywords =
      this.keywords.length > 0
        ? this.keywords.join(this.SPLITTER_KEYWORDS)
        : '';
  }

  /**
   * goes back in navigation history
   */
  back() {
    this.utilService.back();
  }

  /**
   * remove item from a list
   * @param data
   */
  removeItem(data: any) {
    if (data.id) {
      this.deindexation.urls.forEach((item) => {
        if (item === data) {
          data.markToDelete = !data.markToDelete;
        }
      });
    } else {
      this.deindexation.urls.forEach((item) => {
        if (item === data) {
          const index = this.deindexation.urls.findIndex((i) => i === data);
          this.deindexation.urls.splice(index, 1);
        }
      });
    }
  }

  /**
   * clear array
   */
  cleanUrlDeindexingFiels(): void {
    this.urlDeindexing = {};
  }

  /**
   * add item to an array
   */
  addItem(): void {
    this.encontrado = false;

    if (this.urlDeindexing !== '') {
      if (this.deindexation.urls.length !== 0) {
        this.deindexation.urls.forEach((obj) => {
          if (
            this.utilService.checkCaracter(obj.url) ===
            this.utilService.checkCaracter(this.urlDeindexing.url)
          ) {
            this.encontrado = true;
          }
        });
        if (!this.encontrado) {
          this.deindexation.urls.push(Object.assign({}, this.urlDeindexing));
          this.cleanUrlDeindexingFiels();
        }
      } else {
        this.deindexation.urls.push(Object.assign({}, this.urlDeindexing));
        this.cleanUrlDeindexingFiels();
      }
    }
  }

  /**
   * Disabled form by status: Just editable when CREATED, REJECTED and null (Null = New Form)
   */
  private checkDisableForm(): void {
    this.disabled = this.deindexation.statusDef != null && !this.deindexation.statusDef.CREATED && !this.deindexation.statusDef.REJECTED;
  }
}
